package com.engagement.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
    		// --swagger ui
    		"/swagger-resources/**",
    		"/swagger-ui.html",
    		"/v2/api-docs",
    		"/webjars/**"
    };
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("In the filter");
    	http.authorizeRequests()
    	.antMatchers(AUTH_WHITELIST).permitAll();
    	http.cors().and().csrf().disable().authorizeRequests(authorize -> authorize
                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        
    }
    
}