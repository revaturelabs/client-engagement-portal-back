package com.engagement.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.engagement.model.Admin;
import com.engagement.service.AdminService;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService as;

	@InjectMocks
	private AdminController ac;

	private String mockAdminJson = "{\"adminId\":0 ,\"email\":\"a@a.net\",\"firstName\":\"admin\",\"lastName\":\"adminson\"}";
	private String mockAdminJson2 = "{\"adminId\":0 ,\"email\":\"a2@a.net\",\"firstName\":\"admin\",\"lastName\":\"adminson\"}";
	Admin admin0 = new Admin(0, "a@a.net", "admin", "adminson");
	Admin admin2 = new Admin(0, "a2@a.net", "admin", "adminson");
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ac).build();
	}

	@Test
	void testCreateNewAdmin() throws Exception {

		Mockito.when(as.register(admin0)).thenReturn(true);
		Mockito.when(as.register(admin2)).thenReturn(false);
		this.mockMvc
				.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("User succesfully created!")));
		
		
		this.mockMvc
		.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson2)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict())
		.andExpect(content().string(containsString("User creation failed!!")));

	}

}
