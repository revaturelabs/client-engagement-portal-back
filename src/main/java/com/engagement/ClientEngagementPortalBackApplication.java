package com.engagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEncryptableProperties
@SpringBootApplication
public class ClientEngagementPortalBackApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ClientEngagementPortalBackApplication.class, args);
	}

}
