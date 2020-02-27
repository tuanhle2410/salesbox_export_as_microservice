package com.salesbox.ExportService;


import com.rabbitmq.client.*;
import com.salesbox.config.AppConfig;
import com.salesbox.entity.TenantDatabase;
import com.salesbox.service.lead.ExportLeadServiceInternal;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

import static com.salesbox.constant.Constant.*;

public class Application
{

    private static List<TenantDatabase> lstTenant = new ArrayList<>();
    public static String currentDBURL = "";
    private static final int prefetchCount = 10;
    private static Properties properties = new Properties();
    public static Logger logger = LoggerFactory.getLogger(Application.class);

    private static Map<String, AnnotationConfigApplicationContext> tenantContextMap = new HashMap<>();

    private static List<TenantDatabase> getListDatabase() throws Exception
    {
        logger.warn("Load tenant list at the first-time");
        HttpResponse response;
        String result = "";
        String urlString = getPropertyByKey(GET_LIST_DATABASE_URL_PROPS);
        {
            do
            {

                HttpGet request = new HttpGet(urlString);
                request.addHeader("Content-Type", "application/json");

                HttpClientBuilder bld = HttpClientBuilder.create();
                HttpClient client = bld.build();
                response = client.execute(request);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");


            } while (response.getStatusLine().getStatusCode() != 200
                    && response.getStatusLine().getStatusCode() != 201
                    && response.getStatusLine().getStatusCode() != 204);
        }

        JSONObject jsonObject = new JSONObject(result);
        List<TenantDatabase> lstTenant = new ArrayList<>();

        if (jsonObject.getJSONArray("tenantDTOList") != null)
        {
            JSONArray tenantDTOList = jsonObject.getJSONArray("tenantDTOList");
            for (int i = 0; i < tenantDTOList.length(); i++)
            {
                TenantDatabase tenantDatabase = new TenantDatabase();
                tenantDatabase.setDatabase((String) ((JSONObject) tenantDTOList.get(i)).get("name"));
                tenantDatabase.setServerUrl((String) ((JSONObject) tenantDTOList.get(i)).get("ip"));
                logger.warn("url: " + tenantDatabase.getServerUrl() + " name: " + tenantDatabase.getDatabase());
                lstTenant.add(tenantDatabase);
            }
        }


        return lstTenant;
    }

    private static String checkDatabase(String database) throws Exception
    {
        String url = "jdbc:postgresql://" + getPropertyByKey(DATABASE_IP_PROPS) + "/" + getPropertyByKey(COMMON_DATABASE_PROPS);

        logger.warn(" [x] Start get database name from chanel: " + database);
        if (database.contains("salesbox"))
        {
            database = database.substring(database.indexOf("salesbox"), database.length());
        }
        else
        {
            database = "salesbox";
        }

        if (!database.equals("salesbox"))
        {
            for (TenantDatabase tenantDatabase : lstTenant)
            {
                if (database.equals(tenantDatabase.getDatabase()))
                {
                    url = "jdbc:postgresql://" + tenantDatabase.getServerUrl() + "/" + tenantDatabase.getDatabase();
                    break;
                }
            }
        }
//        else
//        {
//            url = "jdbc:postgresql://" + getPropertyByKey(DATABASE_IP_PROPS) + "/" + getPropertyByKey(COMMON_DATABASE_PROPS);
//        }

        return url;
    }

    public static void main(String[] args) throws Exception {
        lstTenant = getListDatabase();

        try
        {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername(getPropertyByKey(RABBITMQ_USERNAME_PROPS));
            factory.setPassword(getPropertyByKey(RABBITMQ_PASSWORD_PROPS));
            factory.setHost(getPropertyByKey(RABBITMQ_HOST_PROPS));
            factory.setPort(Integer.parseInt(getPropertyByKey(RABBITMQ_PORT_PROPS)));

            Connection conn = factory.newConnection();

            Channel channel = conn.createChannel();


            Map<String, Object> argsChanel = new HashMap<String, Object>();

            channel.queueDeclare(getPropertyByKey(QUEUE_NAME_PROPS), true, false, false, argsChanel);
            channel.basicQos(prefetchCount);

            Consumer consumer = new DefaultConsumer(channel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException
                {
                    String message = new String(body, StandardCharsets.UTF_8);
                    logger.warn("====================START===========================");
                    logger.warn("MESSAGE:");
                    logger.warn(message);

                    String bodyResponse = "";
                    String database = "";

                    try
                    {
                        bodyResponse = message;

                        JSONObject resultJson = new JSONObject(message);

                        if(!resultJson.has("value"))
                        {
                            logger.warn("There is no data");
                            logger.warn("==================END=============================");
                            return;
                        }

                        JSONArray jsonArray = (JSONArray) resultJson.get("value");
                        int length = jsonArray.length();
                        JSONObject valueObject = (JSONObject) jsonArray.get(length - 1);
                        database = valueObject.getString("clientState");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                    try
                    {
                        String dbUrl = "";

                        if (!database.equals("salesbox"))
                        {
                            dbUrl = checkDatabase(database);
                            Application.currentDBURL = dbUrl;
                        }

                        logger.warn(" [x] DATABASE URL: " + dbUrl);
                        logger.warn(" [x] Start Spring Application ");

                        AnnotationConfigApplicationContext context = null;
                        if(tenantContextMap.get(dbUrl) != null)
                        {
                            context = tenantContextMap.get(dbUrl);
                            logger.warn(" Existing Spring context with " + dbUrl);
                        }
                        else
                        {
                            context = new AnnotationConfigApplicationContext(AppConfig.class);
                            tenantContextMap.put(dbUrl, context);
                        }

                        ExportLeadServiceInternal exportLeadServiceInternal = context.getBean(ExportLeadServiceInternal.class);
                        logger.warn(" [x] Start Spring Application DONE!!!");

                        //sync email
                        if (!bodyResponse.isEmpty())
                        {
                            logger.warn("Start saving email:");
                        }
                        else
                        {
                            logger.warn("webhook error!");
                        }

                        logger.warn("====================END=============================");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            };
            channel.basicConsume(getPropertyByKey(QUEUE_NAME_PROPS), true, consumer);
            logger.warn("Office 365 Email-Contact-Service started!");

        }
        catch (IOException e)
        {
            StringWriter outError = new StringWriter();
            e.printStackTrace(new PrintWriter(outError));
            String errorString = outError.toString();
            System.err.println("IOException: " + e.getMessage());
            logger.warn(errorString);
        }
        catch (TimeoutException e)
        {
            System.err.println("TimeoutException: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
        }

    }
    private static String getPropertyByKey(String key)
    {
        return properties.getProperty(key);
    }
}
