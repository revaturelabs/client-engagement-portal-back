package com.engagement.util;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	@Bean
	public void FirebaseInit() throws IOException {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.getApplicationDefault())
				.setDatabaseUrl("")
				.build();
		
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);			
		}
	}
}
