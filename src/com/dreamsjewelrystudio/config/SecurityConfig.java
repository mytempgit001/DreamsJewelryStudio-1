package com.dreamsjewelrystudio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired private DataSource ds;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(3);
//		http.csrf().disable();
//		http.authorizeRequests()
//      		.antMatchers("/cart")
//      		.anonymous()
//  		.and()
//			.rememberMe()
//			.tokenRepository(persistenceTokenRepository())
//			.rememberMeCookieName("rememberme")
//            .tokenValiditySeconds(60 * 60 * 24) 
//            .alwaysRemember(true);
//          .useSecureCookie(true);
	}
	
	@Bean
    public PersistentTokenRepository persistenceTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(ds);
        return db;
    }
}
