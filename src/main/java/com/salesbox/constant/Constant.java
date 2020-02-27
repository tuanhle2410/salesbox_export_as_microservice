package com.salesbox.constant;

import com.salesbox.entity.enums.CommunicationHistoryType;
import com.salesbox.entity.enums.CommunicationType;
import com.salesbox.entity.enums.SalesMethodActivityType;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 4/23/14
 * Time: 6:54 PM
 */
public class Constant
{
    public static final String QUEUE_NAME_PROPS = "queue.name";
    public static final String GET_LIST_DATABASE_URL_PROPS = "get.list.database.url";
    public static final String COMMON_DATABASE_PROPS = "common.database";
    public static final String DATABASE_IP_PROPS = "database.ip";

    public static final String RABBITMQ_HOST_PROPS = "rabbitmq.host";
    public static final String RABBITMQ_USERNAME_PROPS = "rabbitmq.username";
    public static final String RABBITMQ_PASSWORD_PROPS = "rabbitmq.password";
    public static final String RABBITMQ_PORT_PROPS = "rabbitmq.port";
    public static final Double PRICE_PER_LICENSE = 20d;

    public static final String FACEBOOK_API_VERSION = "v2.12";

    public static final String PERMISSION_READ_ID = "a4de4309-5f19-420e-a6c0-aba0fe57814a";
    public static final String PERMISSION_WRITE_ID = "42958641-a33a-461d-93f5-574824d6f3b1";
    public static final String PERMISSION_DELETE_ID = "a4d54604-6c0f-4681-bec3-5aa226db2ed8";
    public static final String PERMISSION_ADMIN_ID = "ae5c2d77-c77d-4b36-844f-40cfbdac005c";

    public static final String SCOPE_ALL_COMPANY_ID = "4ecd76e4-14a1-45e4-a1ee-e4d4514c153c";
    public static final String SCOPE_OWN_UNIT_ID = "c33c5d86-2230-46af-b401-4bf5ebdb3362";
    public static final String SCOPE_OWN_OBJECTS_ID = "563665e8-0195-4d35-be7c-d872b4ed67f5";
    public static final String SCOPE_NONE_ID = "1910cbfb-f363-494f-a07d-45d490dd10a8";

    public static final String PERMISSION_READ_NAME = "READ";
    public static final String PERMISSION_WRITE_NAME = "WRITE";
    public static final String PERMISSION_DELETE_NAME = "DELETE";
    public static final String PERMISSION_ADMIN_NAME = "ADMIN";

    public static final String SCOPE_ALL_COMPANY_NAME = "ALL_COMPANY";
    public static final String SCOPE_OWN_UNIT_NAME = "OWN_UNIT";
    public static final String SCOPE_OWN_OBJECTS_NAME = "OWN_OBJECTS";
    public static final String SCOPE_NONE_NAME = "NONE";

    public static final String DEFAULT_ENTERPRISE = "3713c3e0-c14d-435a-886e-ecde9e692ec9";
    public static final UUID SALESBOX_ENTERPRISE_UUID = UUID.fromString("9efba6c7-6864-4349-b3da-9dd558ed826c");

    public static final String DEFAULT_COMPANY_SIZE = "90c8d31e-9a2d-44a0-8e06-06b7d55714cb";

    public static UUID DEFAULT_NONE_TASK_TAG_ID = UUID.fromString("06410908-7775-4b3a-bd67-05b4d127c865");
    public static String EXTERNAL_FOLLOW_UP_TASK_TAG_NAME = "EXTERNAL_FOLLOW_UP";

    public static final String DEFAULT_FILE_LOCATION = "/srv/files";
    public static final String DEFAULT_JSON_FILE_LOCATION = "/srv/syncs";
//    public static final String DEFAULT_JSON_FILE_LOCATION = "/Users/hungcr/files/syncs";

    public static final String DEFAULT_LANGUAGE_LOCATION = "salesbox";
    public static final String DEFAULT_TEMPLATE_LOCATION = "salesbox/templates";
    public static final String DEFAULT_REPORT_IMAGE_LOCATION = "salesbox/templates/imgs/reportImg";
    public static final String DEFAULT_REPORT_TEMPLATE_LOCATION = "salesbox/reportTemplate";

    public static final String TEMPLATE_OPPORTUNITY_FILE = "Opportunity.xls";
    public static final String TEMPLATE_PRODUCT_FILE = "Product.xls";

    public static final String DEFAULT_WEB_TOKEN = "WEB_TOKEN";

    public static final String MEETING_PER_WEEK = "Appointments per week";
    public static final String CALLS_PER_WEEK = "Calls per week";
    public static final String DIALS_PER_WEEK = "Dials per week";
    public static final String MEDIAN_DEAL_TIME = "Median deal time";
    public static final String MEDIAN_DEAL_SIZE = "Median deal size";
    public static final String MEDIAN_LEAD_TIME = "Median lead time";
    public static final String DEFAULT_CONTRACT_DATE = "Contract date";
    public static final String HOURS_PER_MEETING = "Hours/meeting";
    public static final String MEETING_PER_DEAL = "Meetings/deal";
    public static final String TRAVELLING_HOURS_PER_MEETING = "Travelling hours/meeting";
    public static final String NUMBER_CALLS_PER_MEETING = "Calls/meeting";
    public static final String MINUTES_PER_CALL = "Minutes/call";
    public static final String NUMBER_PICKS_PER_CALL = "Picks/call";
    public static final String MINUTES_PER_PICK = "Minutes/pick";
    public static final String HOURS_PER_QUOTE = "Hours/quote";
    public static final String SEND_QUOTE_PER_WEEK = "Send quotes per week";
    public static final String SEND_CONTRACT_PER_WEEK = "Send contracts per week";
    public static final String HOURS_PER_CONTRACT = "Hours/contract";
    public static final String WORK_DATA_MARGIN = "Margin";
    public static final String WORK_DATA_ORDER_VALUE = "Order value";
    public static final String FISCAL_YEAR = "Start M/D";
    public static final String CURRENCY = "Currency";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String REPORT_DATE_FORMAT = "dd-MM-yyyy'T'HH:mm:ss.SSSZ";
    public static final String defaultTimeFiscalYear = "T00:00:00.000";
    public static final String DECIMAL_FORMAT = "###.##";
    public static final String GAP_ANALYSIS_THRESHOLD = "Gap analysis threshold";
    public static final String HOURS_PER_WORK_DAY = "Hours/workday";
    public static final String WORK_DAYS_PER_WEEK = "No workdays/week";
    public static final String TARGET_MARGIN = "Target margin";
    public static final String MARGIN_WARNING = "Margin";
    public static final String REVENUE_WARNING = "Revenue";
    public static final String ORDER_WARNING = "Order value";
    public static final String PROFIT_WARNING = "Profit";
    public static final String PROSPECT_WARNING = "Prospect";
    public static final String ORDERS_ROW_TYPE = "Order rows type";
    public static final String MEETING_WARNING = "Meeting";
    public static final String SUFFIX_REGISTER = "/pre-register/";
    public static final String DEFAULT_VALUE_FOR_SEND_QUOTE_PER_WEEK = "3";
    public static final String DEFAULT_VALUE_FOR_SEND_CONTRACT_PER_WEEK = "1";
    public static final List<CommunicationType> contactAdditionalPhoneTypes = Arrays.asList(
            CommunicationType.PHONE_HOME,
            CommunicationType.PHONE_HOME_FAX,
            CommunicationType.PHONE_IPHONE,
            CommunicationType.PHONE_MOBILE,
            CommunicationType.PHONE_WORK,
            CommunicationType.PHONE_WORK_FAX,
            CommunicationType.PHONE_OTHER,
            CommunicationType.PHONE_MAIN
    );

    public static final List<CommunicationType> contactAdditionalEmailTypes = Arrays.asList(
            CommunicationType.EMAIL_HOME,
            CommunicationType.EMAIL_ICLOUD,
            CommunicationType.EMAIL_WORK,
            CommunicationType.EMAIL_OTHER
    );

    public static final List<CommunicationType> contactCommunicationTypes = Arrays.asList(
            CommunicationType.PHONE_HOME,
            CommunicationType.PHONE_HOME_FAX,
            CommunicationType.PHONE_IPHONE,
            CommunicationType.PHONE_MOBILE,
            CommunicationType.PHONE_WORK,
            CommunicationType.PHONE_WORK_FAX,
            CommunicationType.PHONE_OTHER,
            CommunicationType.PHONE_MAIN,

            CommunicationType.EMAIL_HOME,
            CommunicationType.EMAIL_ICLOUD,
            CommunicationType.EMAIL_WORK,
            CommunicationType.EMAIL_OTHER

    );

    public static final List<CommunicationType> organisationAdditionalPhoneTypes = Arrays.asList(
            CommunicationType.PHONE_HEAD_QUARTER,
            CommunicationType.PHONE_SUBSIDIARY,
            CommunicationType.PHONE_DEPARTMENT,
            CommunicationType.PHONE_UNIT
    );

    public static final List<CommunicationType> organisationAdditionalEmailTypes = Arrays.asList(
            CommunicationType.EMAIL_HEAD_QUARTER,
            CommunicationType.EMAIL_SUBSIDIARY,
            CommunicationType.EMAIL_DEPARTMENT,
            CommunicationType.EMAIL_UNIT
    );

    public static final List<CommunicationType> organisationCommunicationTypes = Arrays.asList(
            CommunicationType.PHONE_HEAD_QUARTER,
            CommunicationType.PHONE_SUBSIDIARY,
            CommunicationType.PHONE_DEPARTMENT,
            CommunicationType.PHONE_UNIT,

            CommunicationType.EMAIL_HEAD_QUARTER,
            CommunicationType.EMAIL_SUBSIDIARY,
            CommunicationType.EMAIL_DEPARTMENT,
            CommunicationType.EMAIL_UNIT
    );

    public static final List<CommunicationType> mainEmailAndPhoneTypes = Arrays.asList(
            CommunicationType.EMAIL_WORK,
            CommunicationType.PHONE_MOBILE
    );

    public static final List<SalesMethodActivityType> quoteSentOrContractSentTypes = Arrays.asList(
            SalesMethodActivityType.CONTRACT_SENT,
            SalesMethodActivityType.QUOTE_SENT
    );

    public static final UUID DEFAULT_SHARED_ORGANISATION_ID = UUID.fromString("1c6f046f-0907-462b-874e-68ccaa22fdc2");
    public static final UUID DEFAULT_SHARED_CONTACT_ID = UUID.fromString("5cf18f20-99f2-4dd3-9353-f1698883ee4e");

    public static final List<CommunicationHistoryType> dialOrCallType = Arrays.asList(
            CommunicationHistoryType.CALL,
            CommunicationHistoryType.DIAL,
            CommunicationHistoryType.FACE_TIME_CALL,
            CommunicationHistoryType.FACE_TIME_DIAL
    );
    public static final List<CommunicationHistoryType> mailType = Arrays.asList(
            CommunicationHistoryType.EMAIL,
            CommunicationHistoryType.EMAIL_SENDER,
            CommunicationHistoryType.EMAIL_RECEIVER
    );

    public static final List<CommunicationHistoryType> callType = Arrays.asList(
            CommunicationHistoryType.CALL,
            CommunicationHistoryType.FACE_TIME_CALL
    );

    public static final List<CommunicationHistoryType> dialType = Arrays.asList(
            CommunicationHistoryType.DIAL,
            CommunicationHistoryType.FACE_TIME_DIAL
    );

    public static final String delimiterDate = "-";
    public static final String FIRST_DELIMITER_FOR_CALL_PER_MEETING = ",";
    public static final String SECOND_DELIMITER_FOR_CALL_PER_MEETING = ";";

    public static final String lessThanHTMLCode = "&lt;";
    public static final String greaterThanHTMLCode = "&gt;";
    public static final String breakLineHTMLCode = "<br/>";

    public static final String DELIMITER = "#!!";
    public static final String DELIMITER_TEMP = "@";
    public static final String VERSION_DELIMITER = "#";

    public static final double PRIORITISED_LIST_THRESHOLD = 0.2;
    public static final double DISTRIBUTION_LIST_THRESHOLD = 0.1;

    public static final int MAX_PROSPECT_AFFECT = 20;
    public static final int MAX_PICK_AFFECT = 500;
    public static final int MAX_CALL_AFFECT = 100;
    public static final int MAX_MEETING_AFFECT = 30;
    public static final Double MAX_MILLISECOND = 86400000D;
    public static final int MAX_MINUTES_BETWEEN_TWO_DIAL = 5;


    public static final int DEFAULT_MEETING = 1;

    public static final int MAX_PAGE_SIZE = 500;
    public static final int MAX_SEARCH_RESULT_SIZE = 1000;

    public static final int DEFAULT_NUMBER_UNIT_PRODUCT = 1;

    public static final List<Integer> salesMethodPrivacyTypeList = Arrays.asList(new Integer[]{0, 1, 3, 4});

    public static final String PARTNER_TYPE = "Partner";

    public static final int DEFAULT_NOTIFICATION_TIME = 2;
    public static final Boolean DEFAULT_NOTIFICATION_STATUS = Boolean.TRUE;
    public static final Integer DEFAULT_LOSE_MEETING_RATIO = 0;

    public static final String NORMAL_SOUND = "normal.caf";

    public static final String WON_OPP_SOUND = "wonOpp.caf";

    public static final Double NUMBER_WEEK_IN_YEAR = 52d;

    public static final Double e = 0.0000001;
    public static final Double eReport = 0.5;

    public static final String reportMail1 = "andreas.lalangas@salesboxcrm.com";
    public static final String reportMail9 = "henrik.ornstedt@salesboxcrm.com";

    // update receive email when user regiter
    public static final String reportMailWhenUserRegiter= "info@salesboxcrm.com";
    public static final List<String> reportWeeklyToMailList = Arrays.asList(
            reportMail1, reportMail9
    );

    public static final List<String> weeklyReportReceiversList = Arrays.asList(
            reportMail1, reportMail9
    );

    public static final int numberExpireDays = 3;

    public static final double numberMillisecondsPerDay = 24 * 60 * 60 * 1000;
    public static final long numberMillisecondsPerSecond = 1000;
    public static final int numberMonthsPerQuarter = 3;

    public static final String appointmentNotFoundErrorMessage = "<br/><b>Sorry, this appointment has been canceled by owner!</b>";

    public static final String reportFileDelimiter = "_";

    public static final double PROSPECT_WARNING_VALUE = 0.85;
    public static final double MEETING_WARNING_VALUE = 0.85;

    public static final String NOT_APPLICABLE = "NA";
    public static final String OTHER = "OTHER";

    public static final Integer CONTACT_OUTLOOK_PAGE_SIZE = 50;
    public static final Integer CONTACT_OFFICE365_PAGE_SIZE = 50;
    public static final Integer CONTACT_GOOGLE_PAGE_SIZE = 50;

    public static final SimpleDateFormat DATE_FORMAT_OUTLOOK = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static final List<String> COMMON_DOMAINS = Arrays.asList(
            /* Default domains included */
            "aol.com", "att.net", "comcast.net", "facebook.com", "gmail.com", "gmx.com", "googlemail.com",
            "google.com", "hotmail.com", "hotmail.co.uk", "mac.com", "me.com", "mail.com", "msn.com",
            "live.com", "sbcglobal.net", "verizon.net", "yahoo.com", "yahoo.co.uk",
            /* Other global domains */
            "email.com", "games.com" /* AOL */, "gmx.net", "hush.com", "hushmail.com", "icloud.com", "inbox.com",
            "lavabit.com", "love.com" /* AOL */, "outlook.com", "pobox.com", "rocketmail.com" /* Yahoo */,
            "safe-mail.net", "wow.com" /* AOL */, "ygm.com" /* AOL */, "ymail.com" /* Yahoo */, "zoho.com", "fastmail.fm",
            "yandex.com",
            /* United States ISP domains */
            "bellsouth.net", "charter.net", "comcast.net", "cox.net", "earthlink.net", "juno.com",
            /* British ISP domains */
            "btinternet.com", "virginmedia.com", "blueyonder.co.uk", "freeserve.co.uk", "live.co.uk",
            "ntlworld.com", "o2.co.uk", "orange.net", "sky.com", "talktalk.co.uk", "tiscali.co.uk",
            "virgin.net", "wanadoo.co.uk", "bt.com",
            /* Domains used in Asia */
            "sina.com", "qq.com", "naver.com", "hanmail.net", "daum.net", "nate.com", "yahoo.co.jp", "yahoo.co.kr", "yahoo.co.id", "yahoo.co.in", "yahoo.com.sg", "yahoo.com.ph",
            /* French ISP domains */
            "hotmail.fr", "live.fr", "laposte.net", "yahoo.fr", "wanadoo.fr", "orange.fr", "gmx.fr", "sfr.fr", "neuf.fr", "free.fr",
            /* German ISP domains */
            "gmx.de", "hotmail.de", "live.de", "online.de", "t-online.de" /* T-Mobile */, "web.de", "yahoo.de",
            /* Russian ISP domains */
            "mail.ru", "rambler.ru", "yandex.ru", "ya.ru", "list.ru",
            /* Belgian ISP domains */
            "hotmail.be", "live.be", "skynet.be", "voo.be", "tvcablenet.be", "telenet.be",
            /* Argentinian ISP domains */
            "hotmail.com.ar", "live.com.ar", "yahoo.com.ar", "fibertel.com.ar", "speedy.com.ar", "arnet.com.ar",
            /* Domains used in Mexico */
            "hotmail.com", "gmail.com", "yahoo.com.mx", "live.com.mx", "yahoo.com", "hotmail.es", "live.com", "hotmail.com.mx", "prodigy.net.mx", "msn.com"
    );


    public static final String DeactivatedUsersUnitName = "Deactivated Users";
    public static final String NoUnitName = "No Unit";

    public static final String ACCOUNT_ZIP_FILE_NAME = "Account.zip";
    public static final String CONTACT_ZIP_FILE_NAME = "Contact.zip";
    public static final String PROSPECT_ZIP_FILE_NAME = "Prospect.zip";
    public static final String TASK_ZIP_FILE_NAME = "Task.zip";
    public static final String CUSTOM_FIELD_ZIP_FILE_NAME = "CustomField.zip";
    public static final String LEAD_ZIP_FILE_NAME = "Lead.zip";
    public static final String APPOINTMENT_ZIP_FILE_NAME = "Appointment.zip";
    public static final String CALLIST_ZIP_FILE_NAME = "CallList.zip";

    public static final String DEFAULT_PRODUCT_GROUP_VISMA_NO_ITEM_CLASS_ENGLISH = "No group in Visma";
    public static final String DEFAULT_PRODUCT_GROUP_VISMA_NO_ITEM_CLASS_SWEDISH = "Ingen grupp i Visma";


    public static final int NUMBER_OF_RETRIES = 3;

    public static Integer MAX_SYNC_PAGE_SIZE = 100000;

    public static Integer MAX_ADD_DEALS_IN_BATCH = 100;
    public static Integer MAX_ADD_UNQUALIFIED_IN_BATCH = 200;


    public static List<String> paidEnterpriseIds = Arrays.asList(
            "9e456a21-15a9-4559-af08-88f83028adb5",
            "322d2c5a-11a9-4653-9cba-3cd3f0b666ef",
            "c3aa2fbf-8e0c-482f-9e78-77ab79a281a5",
            "8d60be47-8b03-44f0-9c80-097753d7bd90",
            "480b9c6c-52a8-4543-9200-888a9ffc9808",
            "fea0573d-b792-4846-a5ec-cda3bbecab34",
            "9efba6c7-6864-4349-b3da-9dd558ed826c",
            "a5bb63eb-4985-42c8-b927-51d2031ec9b5",
            "09d9b63c-0ef7-4a85-bc94-af8183d39a85",
            "50c56388-cbdd-4a80-b714-9d5390e1c4bb",
            "2ac78b1e-ccd7-4af3-a6b6-c6ff6f9611c7",
            "3ed0e522-c0ee-417c-b2da-3bacc5413a90",
            "d83567a1-4bfb-407f-8792-ad7646fc08c8",
            "d90a5359-ea6b-44ec-b66e-805e7f476e5a",
            "9d39fa5a-f9ad-47ea-aea4-c8ac7374bc47",
            "26f548b8-bac9-468f-a50b-ccc868d10c2b",
            "09188c73-5209-427a-b9e8-b0234c3bca6d",
            "8324e31f-628d-4147-aa10-44567e5940d6",
//            "b33df17e-c4f3-4dd2-a703-deba4c30c290", //removed Sinlle
            "92b3da85-93f4-4c42-bcc8-f8bd2838d341",
            "ed634fc6-c960-4782-b154-8856b606b749",
            "95bda462-6333-4b07-9506-c79b19ef8ea0",
            "b51757e8-9120-4563-8ca8-7168bc030a70",
            "72489f37-e086-427c-b56f-a37267091404",
            "7b59bc24-ac65-466e-8dfc-80a2f20380c8",
            "e2111ae8-11fa-4a6b-8267-eb5c0c8ad64b",
            "b4e62c86-bed9-453b-9bfb-69188b3a6a56",
            "7d021f35-d55a-4577-9cde-26c68d04c901",
            "50410166-7330-49c5-a2ca-1ee4428ef97a",
            "5ac05fcb-b648-4358-8c12-881dac0661a6",
            "d572972b-53ed-488e-afba-f3b0a435b427",
            "b8430f85-bd09-4e07-8b7c-97bce7a99994",
            "c21ded96-8877-489f-962f-c1841191a58a",
            "4ba9c624-bf4a-44cd-ac44-a7b7a9addd33",
            "6dd764a0-8642-4164-9514-597294b6c7a8",
//            "013f04bb-049a-46f0-9e8f-42ea0c783e8d", //Remove accessIT
            "33846a3f-7ffa-46b7-8ff3-bc9335ff3226",
            "6595470d-6054-4c48-96b0-b34d1408af98",
            "d572972b-53ed-488e-afba-f3b0a435b427",
            "1f7524ba-d872-4042-97a5-25a5cc70cb79",
            "3389bc69-253d-4a10-a38f-c0cf34ffe2c6",
            "490e63f1-8f4b-47f3-b8df-a99f0d815df9",
            "91818072-ad0f-4243-ba65-e132223a696c",
            "8bb99b99-b786-4661-b93a-39f4c7124d3b",
            "dcad23c7-a45a-4c61-8bf9-6961fd9f31fc",
            "fca312f1-188f-4f65-9667-2a12767398cb",
            "b51757e8-9120-4563-8ca8-7168bc030a70",
            "2529b4fe-ed1e-4488-b1ae-03d284823c9f" //primepower
    );


}