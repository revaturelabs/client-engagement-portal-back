package com.engagement.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Brooke Wursten et. al.
 * 
 * This class contains all the unit tests for the admin Controller
 *
 */
@ExtendWith(MockitoExtension.class)
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
	Admin admin2 = new Admin(1, "a2@a.net", "admin", "adminson");
	BatchName namedBatch = new BatchName("TR-1759", "Mock Batch 505");

	
	/**
	 * Creates a mock servlet to use in the controller tests
	 */
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(ac).build();
	}
	
	
	/**
	 * @author Brooke Wursten
	 * This unit test checks whether the controller logic for successfully creating a new admin object works properly.
	 * @throws Exception
	 */
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
	
	/**
	 * @author Brooke Wursten
	 * This unit test checks whether the controller logic for unsuccessfully creating a new admin object works properly.
	 * @throws Exception
	 */
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

	/**
	 * @author Brooke Wursten
	 * This unit test checks whether the controller logic for successfully updating a new admin object works properly.
	 * @throws Exception
	 */
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
	
	/**
	 * @author Brooke Wursten
	 * This tests the controller logic for when an admin update can not be completed because the admin doesn't exist.
	 * @throws Exception
	 */
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
	
	/**
	 * This unit test checks whether the controller logic for when updating fails for some other reason.
	 * @throws Exception
	 */
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
	
	/**
	 * @author Brooke Wursten
	 * This unit test checks whether the controller logic for successfully deleting a new admin object works properly.
	 * @throws Exception
	 */
	@Test
	void testDeleteAdminSuccess() throws Exception {
		Mockito.when(as.findByAdminId(0)).thenReturn(admin);
		this.mockMvc
				.perform(delete("/admin/delete").accept(MediaType.ALL).param("id", "0"))
				.andExpect(status().isOk());
	}
	
	/**
	 * @author Brooke Wursten
	 * This unit tests the controller logic for when one tries to delete a user that does not exist
	 * @throws Exception
	 */
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
		List<BatchName> batchesByName = new ArrayList<>();
		batchesByName.add(namedBatch);
		Mockito.when(as.getAllBatches()).thenReturn(batchesByName);
		
		// Makes sure that caliber is up and running
		this.mockMvc
				.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
				.andExpect(status().isOk());

		// Is not really doing anything meaningful
		this.mockMvc
			.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
			.andReturn();

	}
	
	/**
	 * @author Brooke Wursten
	 * Unit test for getting map of batches and clients
	 * @throws Exception
	 */
	@Test
	void testMappedBatchesClients() throws Exception {
		
		/*
		 * mock map we are pretending is returned by AdminService
		 */
		Map<String,Integer> mockMap = new HashMap<String,Integer>(){{
			put("TR-101",1);
			put("TR-102",1);
			put("TR-103",2);
			}};
			Mockito.when(as.findAllMappings())
			.thenReturn(mockMap);
			
		String mockMapJson = new ObjectMapper().writeValueAsString(mockMap);//What we expect to be in the body of the response
		
		
		
		this.mockMvc.perform(get("/admin/mappedBatchesClients").accept(MediaType.ALL))
		.andExpect(content().json(mockMapJson));//Makes the request and ensures we receive the proper json
	}
	
	/**
	 * @author daniel constantinescu
	 * unit test for mapping batch to client when batchId found
	 * @throws Exception
	 */
	@Test
	void testmapBatchToClientSucces() throws Exception {
		String batchId="ABC";
		String email="a@b";
		Mockito.when(as.MapBatchtoClient(batchId, email)).thenReturn(true);
		this.mockMvc
				.perform(put("/admin/mapBatchToClient?batchId=ABC&email=a@b").accept(MediaType.ALL))
				.andExpect(status().isOk());
				
		
	}
	
	
	/**
	 * @author daniel constatinescu
	 * unit test for mapping batch to client when batchId not found
	 * @throws Exception
	 */
	@Test
	void testmapBatchToClientFail() throws Exception {
		String batchId="ABC";
		String email="a@b";
		Mockito.when(as.MapBatchtoClient(batchId, email)).thenReturn(false);
		this.mockMvc
			.perform(put("/admin/mapBatchToClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isConflict());
			
	}

	@Test
	void testUnmapBatchFromClientSucces() throws Exception {
		String batchId="ABC";
		String email="a@b";
		Mockito.when(as.UnMapBatchFromClient(batchId, email)).thenReturn(true);
		this.mockMvc
				.perform(put("/admin/unmapBatchFromClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
		
	}
	@Test
	void testUnmapBatchFromClientFail() throws Exception {
		String batchId="ABC";
		String email="a@b";
		Mockito.when(as.UnMapBatchFromClient(batchId, email)).thenReturn(false);
		this.mockMvc
				.perform(put("/admin/unmapBatchFromClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
				
		
	}
	
}


