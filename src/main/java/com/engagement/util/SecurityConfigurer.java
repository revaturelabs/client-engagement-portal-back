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
    		"/webjars/**",
    		"/swagger-ui/**"
    };
    
	/**
	 * Overriding the default implementation of the filter given to us 
	 * by Spring Security.  We disable CORS restrictions, and make it to where
	 * any request that comes into the server is authenticated first before 
	 * reaching our resources.  The only requests that are not authenticated
	 * pertain to Swagger-ui and its resouces.
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    	.antMatchers(AUTH_WHITELIST).permitAll();
    	http.cors().and().csrf().disable().authorizeRequests(authorize -> authorize
                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        
    }
    
}