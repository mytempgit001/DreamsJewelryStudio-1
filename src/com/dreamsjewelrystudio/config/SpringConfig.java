package com.dreamsjewelrystudio.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dreamsjewelrystudio.util.CatalogPagination;
import com.dreamsjewelrystudio.util.PayPalIntegrator;
import com.dreamsjewelrystudio.util.Util;
import com.dreamsjewelrystudio.util.administration.AdminDataManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.dreamsjewelrystudio.repository")
@ComponentScan(basePackages = {"com.dreamsjewelrystudio.service"})
@PropertySource("classpath:/com/dreamsjewelrystudio/app.properties")
public class SpringConfig {
	
	@Autowired private Environment env;
	@Autowired private DataSource ds;
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(env.getProperty("database.url"));
		ds.setUsername(env.getProperty("database.username"));
		ds.setPassword(env.getProperty("database.password"));
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		return ds;
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(ds);
        entityManagerFactoryBean.setPackagesToScan(new String[] {"com.dreamsjewelrystudio.models"});

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }
	
	public Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.format_sql", true);
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");
        return hibernateProperties;
    }
	
	@Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    @Bean(name="pagination")
    public CatalogPagination getPager() {
    	Util.PRODUCTS_AMOUNT = new JdbcTemplate(ds).queryForObject("Select count(*) from product", Integer.class);
    	return new CatalogPagination(Integer.parseInt(env.getProperty("pagination.size")));
    }
    
    @Bean
    public PayPalIntegrator getPayPalIntegrator() {
    	String clientID = env.getProperty("paypal.clinetID");
    	String secret = env.getProperty("paypal.secret");
    	String mode = env.getProperty("paypal.mode");
    	return new PayPalIntegrator(clientID, secret, mode);
    }
    
    @Bean
    @Lazy(true)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Lazy(true)
    public AdminDataManager dataManager() {
    	System.out.println("AdminDataManager is initializing...");
    	return new AdminDataManager();
    }	
}
