package com.engagement.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.engagement.service.AdminService;

public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService as;
	
	@InjectMocks
	private AdminController ac;
	
	@Test
	void findAllClient() {
		
	}
	
	@Test
	void saveClient() {
		
	}
	
	@Test
	void findById() {
		
	}
	
	@Test
	void findByEmail() {
		
	}
}
