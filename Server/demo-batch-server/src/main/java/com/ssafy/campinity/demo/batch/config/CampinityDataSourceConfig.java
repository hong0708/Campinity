package com.ssafy.campinity.demo.batch.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ConfigurationProperties(prefix="spring.campinity-db.datasource")
@PropertySource({"classpath:application.yml"})
@EnableJpaRepositories(
        basePackages = "com.ssafy.campinity.core.repository",
        entityManagerFactoryRef = "campinityEntityManager",
        transactionManagerRef = "campinityTransactionManager"
)
public class CampinityDataSourceConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean campinityEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(campinityDataSource());
        em.setPackagesToScan("com.ssafy.campinity.core.entity");
        em.setPersistenceUnitName("campinityEntityManager");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
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
    public DataSource campinityDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mariadb://localhost:3306/campinity");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");
        return dataSourceBuilder.build();
    }

    @Bean
    public PlatformTransactionManager campinityTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(campinityEntityManager().getObject());
        return transactionManager;
    }
}