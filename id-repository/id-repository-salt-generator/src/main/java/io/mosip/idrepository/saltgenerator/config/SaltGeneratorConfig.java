//package io.mosip.idrepository.saltgenerator.config;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.hibernate.cfg.AvailableSettings;
//import org.springframework.boot.autoconfigure.batch.BatchDataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DATASOURCE_ALIAS;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DATASOURCE_DRIVERCLASSNAME;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DATASOURCE_PASSWORD;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DATASOURCE_URL;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DATASOURCE_USERNAME;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.DB_SCHEMA_NAME;
//import static io.mosip.idrepository.saltgenerator.constant.SaltGeneratorConstant.PACKAGE_TO_SCAN;
//
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
//
//import jakarta.persistence.EntityManagerFactory;
///**
// * The Class SaltGeneratorIdMapDataSourceConfig - Provides configuration for Salt
// * generator application.
// *
// * @author Manoj SP
// */
//@Configuration
//@EnableTransactionManagement
//
//public class SaltGeneratorConfig {
//	@Autowired
//	private Environment env;
//
//	/** The naming resolver. */
//	@Autowired
//	private PhysicalNamingStrategyResolver namingResolver;
//
//	@Autowired
//	private DatabaseRouter databaseRouter;
//	/**
//	 * Batch config
//	 *
//	 * @return the batch configurer
//	 */
//
//	@Bean
//	@Primary
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(databaseRouter.primaryDataSource());
//		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//		em.setPackagesToScan(PACKAGE_TO_SCAN.getValue());
//		em.setJpaPropertyMap(additionalProperties());
//		return em;
//	}
//
//	/**
//	 * Additional properties.
//	 *
//	 * @return the map
//	 */
//	private Map<String, Object> additionalProperties() {
//		Map<String, Object> jpaProperties = new HashMap<>();
//		jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//		jpaProperties.put("hibernate.physical_naming_strategy", namingResolver);
//		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//		return jpaProperties;
//	}
//
//
//	@Bean
//	@Primary
//	public JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
//		return new JpaTransactionManager(emf);
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		return new DataSourceTransactionManager(databaseRouter.dataSource());
//	}
//
//}
