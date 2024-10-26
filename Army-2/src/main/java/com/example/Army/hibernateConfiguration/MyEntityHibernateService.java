package com.example.Army.hibernateConfiguration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.Army.ArmySystem"}, // Multiple base packages
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
@EnableTransactionManagement
public class MyEntityHibernateService {

    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/***") //set your db
                .username("***") //set your username
                .password("***") //set your password
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
    @Primary
    @Bean("primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource);
        em.setPackagesToScan("com.example.Army.ArmySystem"); // Adjust to your model package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
         em.setJpaProperties(hibernateProperties());
        return em;
    }
    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
        return new JpaTransactionManager(primaryEntityManagerFactory);
    }

     private Properties hibernateProperties() {
         Properties properties = new Properties();
         properties.put("hibernate.hbm2ddl.auto", "create-drop");
         properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
         return properties;
     }
}
