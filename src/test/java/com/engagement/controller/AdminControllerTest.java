package com.engagement.controller;

<<<<<<< HEAD

=======
>>>>>>> 4d602659b38f8b99b54a4730a13f83c1bded15f0
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.model.Admin;
import com.engagement.service.AdminService;


@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService as;

	@InjectMocks
	private AdminController ac;

	private String mockAdminJson = "{\"adminId\":0 ,\"email\":\"a@a.net\",\"firstName\":\"admin\",\"lastName\":\"adminson\"}";
	private String mockAdminJson2 = "{\"adminId\":1 ,\"email\":\"a2@a.net\",\"firstName\":\"admin\",\"lastName\":\"adminson\"}";
	Admin admin0 = new Admin(0, "a@a.net", "admin", "adminson");
	Admin admin2 = new Admin(1, "a2@a.net", "admin", "adminson");

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ac).build();
	}

	@Test
	void testCreateNewAdmin() throws Exception {

		Mockito.when(as.save(admin0)).thenReturn(true);
		Mockito.when(as.save(admin2)).thenReturn(false);
		this.mockMvc
				.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("User succesfully created!")));

		this.mockMvc
				.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson2)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict()).andExpect(content().string(containsString("User creation failed!")));

	}

	@Test
	void testUpdateAdmin() throws Exception {
		Mockito.when(as.update(admin0)).thenReturn(admin0);
		Mockito.when(as.update(admin2)).thenReturn(null);
		this.mockMvc
				.perform(post("/admin/update").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andExpect(content().string(containsString("User updated succesfully!")));

		this.mockMvc
				.perform(post("/admin/update").contentType(MediaType.APPLICATION_JSON).content(mockAdminJson2)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict()).andExpect(content().string(containsString("Update failed")));

	}
	
	@Test
	void testDeleteAdmin() throws Exception {
		Mockito.when(as.findByAdminId(0)).thenReturn(admin0);
		Mockito.when(as.findByAdminId(1)).thenReturn(null);
		this.mockMvc
				.perform(post("/admin/delete").accept(MediaType.ALL).param("id", "0"))
				.andExpect(status().isOk());

		this.mockMvc
			.perform(post("/admin/delete").accept(MediaType.ALL).param("id", "1"))
			.andExpect(status().isConflict()).andExpect(content().string(containsString("User not found!")));

	}

}
