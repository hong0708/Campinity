package com.ssafy.campinity.demo.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({ "classpath:application.yml" })
@ConfigurationProperties(prefix="spring.batch-db.datasource")
@EnableJpaRepositories(
        basePackages = {"com.ssafy.campinity.demo.batch.repository"},
        entityManagerFactoryRef = "batchEntityManager",
        transactionManagerRef = "batchTransactionManager"
)
public class BatchDataSourceConfig{

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean batchEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(batchDataSource());  // batch datasource 주입
        em.setPackagesToScan("com.ssafy.campinity.demo.batch.entity"); // batch entity 위치
        em.setPersistenceUnitName("batchEntityManager");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put("hibernate.implicit_naming_strategy",
                "org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl");
        properties.put("hibernate.physical_naming_strategy",
                "com.ssafy.campinity.core.utils.SnakeCasePhysicalNamingStrategy");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    public DataSource batchDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mariadb://localhost:3306/batch");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");
        return dataSourceBuilder.build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager batchTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(batchEntityManager().getObject());
        return transactionManager;
    }
}