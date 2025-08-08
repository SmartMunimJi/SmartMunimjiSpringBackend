package com.smartmunimji.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties; // Import this
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import javax.sql.DataSource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import java.util.HashMap; // Potentially needed for Hibernate properties

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.smartmunimji.ecommerce.daos", // DAOs package for ecommerce
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager"
)
public class EcommerceDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.secondary") // Bind properties to DataSourceProperties
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(@Qualifier("secondaryDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build(); // Build from properties
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("secondaryDataSource") DataSource dataSource) {
        
        // You might need to add Hibernate properties here for the secondary DB as well
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // Or 'none' or 'validate'
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"); // Or MySQL8Dialect if older Hibernate
        // Add other properties if necessary, e.g., naming strategy
        
        return builder.dataSource(dataSource)
                .packages("com.smartmunimji.ecommerce.entities") // Entities package for ecommerce
                .persistenceUnit("secondary")
                .properties(properties) // Apply properties
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}