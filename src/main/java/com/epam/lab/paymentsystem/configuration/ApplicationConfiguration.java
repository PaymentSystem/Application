package com.epam.lab.paymentsystem.configuration;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class declares three @Bean methods and may be processed
 * by the Spring container using {@link Configuration @Configuration} to generate bean
 * definitions and service requests for those beans at runtime.
 * {@code ApplicationConfiguration} class configures the following back-end application components:
 * database and Hibernate including entityManager and transactionManager.
 *
 * @author unascribed
 * @since 0.0.1
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.epam.lab.paymentsystem.repository")
public class ApplicationConfiguration {

  /**
   * Reading the file application.properties declared in {@link PropertySource @PropertySource}
   * and inject properties in Environment variable.
   */
  @Autowired
  private Environment environment;

  /**
   * This @Bean method produces connection to the database with
   * the settings form environment variable. Invoked by the containing
   * {@code LocalContainerEntityManagerFactoryBean}.
   *
   * @return connection to the database
   */
  @Bean
  public DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(environment.getProperty("spring.database-driver"));
    dataSource.setUrl(environment.getProperty("spring.datasource.url"));
    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
    dataSource.setPassword(environment.getProperty("spring.datasource.password"));
    return dataSource;
  }

  /**
   * This @Bean method creates {@code LocalContainerEntityManagerFactoryBean}
   * which produces a container-managed Entity Manager Factory,
   * using {@code dataSource()} @Bean method and Hibernate as implementation
   * of the Java Persistence API (JPA) specification.
   *
   * @return Entity Manager Factory
   * @link org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("com.epam.lab.paymentsystem");

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());

    return em;
  }

  /**
   * Method configure Hibernate properties.
   *
   * @return Hibernate properties
   */
  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    properties.setProperty(
        "hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
    return properties;
  }

  /**
   * This transaction manager is appropriate for handling distributed transactions,
   * i.e. transactions that span multiple resources, and for controlling transactions
   * on application server resources (e.g. JDBC DataSources available in JNDI) in general.
   * For a single JDBC DataSource, DataSourceTransactionManager is perfectly sufficient,
   * and for accessing a single resource with Hibernate (including transactional cache),
   * HibernateTransactionManager is appropriate, for example.
   *
   * @param emf Entity Manager Factory
   * @return Transaction Manager
   * @link org.springframework.transaction
   * @link org.springframework.transaction.jta.JtaTransactionManager
   */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }
}
