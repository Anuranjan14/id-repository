package io.mosip.idrepository.saltgenerator.config;

import com.zaxxer.hikari.HikariDataSource;
import io.mosip.idrepository.saltgenerator.constant.DatabaseType;
import io.mosip.idrepository.saltgenerator.service.RoutingDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//public class DatabaseRouter {
//
//    @Bean
//    @Primary
//    public DataSource primaryDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idmap");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("PI7vp1Q1Sp");
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        return dataSource;
//    }
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "datasource.identity")
//    public DataSource identityDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idmap");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("PI7vp1Q1Sp");
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        return dataSource;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "datasource.vid")
//    public DataSource vidDataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idmap");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("PI7vp1Q1Sp");
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        return dataSource;
//    }
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DatabaseType.PRIMARY, primaryDataSource());
//        targetDataSources.put(DatabaseType.IDENTITY, identityDataSource());
//        targetDataSources.put(DatabaseType.VID, vidDataSource());
//
//        RoutingDataSource routingDataSource = new RoutingDataSource();
//        routingDataSource.setDefaultTargetDataSource(primaryDataSource());
//        routingDataSource.setTargetDataSources(targetDataSources);
//        routingDataSource.afterPropertiesSet();
//        return routingDataSource;
//    }
//}

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "io.mosip.idrepository.saltgenerator", // ✅ Update this to your actual JPA repository package
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DatabaseRouter {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource primaryDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idmap");
        dataSource.setUsername("postgres");
        dataSource.setPassword("PI7vp1Q1Sp");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.identity")
    public DataSource identityDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idmap");
        dataSource.setUsername("postgres");
        dataSource.setPassword("PI7vp1Q1Sp");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.vid")
    public DataSource vidDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://dev0.mosip.net:5433/mosip_idrepo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("PI7vp1Q1Sp");
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.PRIMARY, primaryDataSource());
        targetDataSources.put(DatabaseType.IDENTITY, identityDataSource());
        targetDataSources.put(DatabaseType.VID, vidDataSource());

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(primaryDataSource());
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();
        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("io.mosip.idrepository.saltgenerator.repository", "io.mosip.idrepository.saltgenerator.entity") // ✅ Update this to your entity package
                .persistenceUnit("default")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}


