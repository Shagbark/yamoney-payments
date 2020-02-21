package ru.yamoney.payments.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Database configuration for the first shard.
 *
 * @author Protsko Dmitry
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "ru.yamoney.payments.repository.shards.third",
        entityManagerFactoryRef = "shard03EntityManagerFactory",
        transactionManagerRef = "shard03JpaTransactionManager"
)
public class Shard03Configuration {


    /**
     * Configures datasource for the first shard.
     * @return configured datasource
     */
    @Bean
    @ConfigurationProperties(prefix = "payments.shards.datasource.third")
    public DataSource shard03DataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    /**
     * Configures entity manager factory for the first shard.
     * @param builder builder for creating entity manager factory
     * @param dataSource configured datasource for the third shard
     *
     * @return configured entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean shard03EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("shard03DataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("ru.yamoney.payments.model.domain")
                .persistenceUnit("shard03")
                .build();
    }

    /**
     * Configures JPA transaction manager for the first shard.
     * @param emf configured entity manager factory
     * @return configured JPA transaction manager
     */
    @Bean
    public JpaTransactionManager shard03JpaTransactionManager(
            @Qualifier("shard03EntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    /**
     * Configure Flyway for the first shard.
     * @param dataSource configured datasource for the third shard
     * @return configured flyway
     */
    @Bean(name = "shard03Flyway", initMethod = "migrate")
    public Flyway flyway(@Qualifier("shard03DataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("/db/migration/payments03")
                .load();
    }

}
