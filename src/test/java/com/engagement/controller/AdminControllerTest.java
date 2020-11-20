package com.engagement.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.engagement.model.dto.BatchName;
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
	Admin admin = new Admin(0, "a@a.net", "admin", "adminson");
	BatchName namedBatch = new BatchName("TR-1759", "Mock Batch 505");

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ac).build();
	}

	@Test
	void testCreateNewAdminSuccess() throws Exception {
		Mockito.when(as.save(admin)).thenReturn(true);
		this.mockMvc
				.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON)
											.content(mockAdminJson)
											.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("Admin successfully created")));
	}
	
	@Test
	void testCreateNewAdminFail() throws Exception {
		Mockito.when(as.save(admin)).thenReturn(false);
		this.mockMvc
				.perform(post("/admin/new").contentType(MediaType.APPLICATION_JSON)
											.content(mockAdminJson)
											.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString("Admin creation failed")));

	}

	@Test
	void testUpdateAdminSuccess() throws Exception {
		Mockito.when(as.findByEmail("a@a.net")).thenReturn(admin);
		Mockito.when(as.update(admin)).thenReturn(admin);
		this.mockMvc
				.perform(put("/admin/update").contentType(MediaType.APPLICATION_JSON)
											.content(mockAdminJson)
											.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted())
				.andExpect(content().string(containsString("Admin updated succesfully")));
	}
	
	@Test
	void testUpdateAdminFailDoesNotExist() throws Exception {
		Mockito.when(as.findByEmail("a@a.net")).thenReturn(null);
		this.mockMvc
				.perform(put("/admin/update").contentType(MediaType.APPLICATION_JSON)
											.content(mockAdminJson)
											.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString("Admin not found")));
	}
	
	@Test
	void testUpdateAdminFail() throws Exception {
		Mockito.when(as.findByEmail("a@a.net")).thenReturn(admin);
		Mockito.when(as.update(admin)).thenReturn(null);
		this.mockMvc
				.perform(put("/admin/update").contentType(MediaType.APPLICATION_JSON)
											.content(mockAdminJson)
											.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(content().string(containsString("Update failed")));
	}
	
	@Test
	void testDeleteAdminSuccess() throws Exception {
		Mockito.when(as.findByAdminId(0)).thenReturn(admin);
		this.mockMvc
				.perform(delete("/admin/delete").accept(MediaType.ALL).param("id", "0"))
				.andExpect(status().isOk());
	}
	
	@Test
	void testDeleteAdminFailDoesNotExist() throws Exception {
		Mockito.when(as.findByAdminId(0)).thenReturn(null);
		this.mockMvc
			.perform(delete("/admin/delete").accept(MediaType.ALL).param("id", "0"))
			.andExpect(status().isConflict())
			.andExpect(content().string(containsString("Admin not found")));
	}
	
	/**
	 * Test that determines whether the names of batches are being correctly imported from caliber
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetBatchNames() throws Exception {
//		List<BatchName> batchesByName = new ArrayList<>();
//		batchesByName.add(namedBatch);
//		Mockito.when(as.getAllBatches()).thenReturn(batchesByName);
		
		// Makes sure that caliber is up and running
		this.mockMvc
				.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
				.andExpect(status().isOk());

		// Is not really doing anything meaningful
		this.mockMvc
			.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
			.andReturn();
	}

}