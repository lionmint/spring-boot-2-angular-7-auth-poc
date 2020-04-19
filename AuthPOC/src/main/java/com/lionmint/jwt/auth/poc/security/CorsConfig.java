package com.lionmint.jwt.auth.poc.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig{

	@Value(value = "${xframe.allowed.origins.local_dev}")
	private String local_dev;
	
	@Value(value = "${xframe.allowed.origins.local_prod}")
	private String local_prod;
	
	@Value(value = "${xframe.allowed.origins.deployed_dev}")
	private String deploy_dev;
	
	@SuppressWarnings("deprecation")
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	registry.addMapping("/api/signup")
            		.allowedOrigins(local_dev, local_prod, deploy_dev);
            	registry.addMapping("/api/login")
        			.allowedOrigins(local_dev, local_prod, deploy_dev);
            	registry.addMapping("/api/login/**")
        			.allowedOrigins(local_dev, local_prod, deploy_dev);
            	registry.addMapping("/api/changepassword")
        			.allowedOrigins(local_dev, local_prod, deploy_dev);
            	registry.addMapping("/api/signup/**")
        			.allowedOrigins(local_dev, local_prod, deploy_dev);
            }
        };
    }
}
