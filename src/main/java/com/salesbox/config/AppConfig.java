package com.salesbox.config;

import com.salesbox.ExportService.Application;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * @author imssbora
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScans(value = {@ComponentScan("com.salesbox.dao.impl"), @ComponentScan("com.salesbox.dao"),
        @ComponentScan("com.salesbox.service")})
public class AppConfig
{

    @Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.url}")
    private String defaultURL;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] properties = new Resource[]{new ClassPathResource("/persistence.properties")};

        ppc.setLocations(properties);
        return ppc;
    }

    @Bean
    public DataSource dataSource()
    {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driver);
        if (Application.currentDBURL.length() == 0)
        {
            dataSource.setUrl(defaultURL);
//            System.out.println("[x] using default url: " + defaultURL);
        }
        else
        {
            dataSource.setUrl(Application.currentDBURL);
        }
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(true);

        dataSource.setInitialSize(10);
        dataSource.setMaxActive(50);
        dataSource.setMaxIdle(20);
        dataSource.setMinIdle(10);
        dataSource.setTestOnConnect(true);
        dataSource.setDefaultTransactionIsolation(2);//read_committed
        dataSource.setDefaultReadOnly(false);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean geEntityManagerFactoryBean()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
        entityManagerFactoryBean.setPersistenceUnitName("LOCAL_PERSISTENCE");

        Properties props = new Properties();
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "false");
        props.put("hibernate.generate_statistics", "false");
        props.put("hibernate.use_sql_comments", "false");
        entityManagerFactoryBean.setJpaProperties(props);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager geJpaTransactionManager()
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(geEntityManagerFactoryBean().getObject());
        return transactionManager;
    }


//    @Bean
//    @Scope(ScopeConst.SINGLETON)
//    public ThreadPoolTaskExecutor taskExecutor()
//    {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(10);
//        threadPoolTaskExecutor.setMaxPoolSize(50);
////        threadPoolTaskExecutor.setQueueCapacity(25);
//        return threadPoolTaskExecutor;
//    }


}
