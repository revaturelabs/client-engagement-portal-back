package com.engagement.controller;

import com.engagement.model.Admin;
import com.engagement.model.dto.BatchName;
import com.engagement.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	 * Tests the find all method in the Admin Controller
	 * @throws Exception 
	 * @author Kelsey Iafrate
	 */
	@Test
	void testFindAll() throws Exception {
		Admin admin1 = new Admin(1, "a@a.net", "firstName", "lastName");
		Admin admin2 = new Admin(2, "b@b.net", "firstName", "lastName");
		List<Admin> admins = new ArrayList<>();
		admins.add(admin1);
		admins.add(admin2);
		
		Mockito.when(as.findAll()).thenReturn(admins);
		
		this.mockMvc
			.perform(get("/admin/")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[*].adminId").value(Matchers.containsInAnyOrder(1,2)))
			.andExpect(jsonPath("$[*].email").value(Matchers.containsInAnyOrder("a@a.net", "b@b.net")))
			.andExpect(jsonPath("$[*].firstName").value(Matchers.containsInAnyOrder("firstName","firstName")))
			.andExpect(jsonPath("$[*].lastName").value(Matchers.containsInAnyOrder("lastName","lastName")));
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
	 * @author Carlo Anselmo
	 * Test that determines whether the names of batches are being correctly imported from caliber
	 * @throws Exception
	 */
	@Test
	void testGetBatchNames() throws Exception {
		List<BatchName> batchesByName = new ArrayList<>();
		batchesByName.add(namedBatch);
		String batchesByNameJson = new ObjectMapper().writeValueAsString(batchesByName);	// Convert list to a json
		Mockito.when(as.getAllBatchNames()).thenReturn(batchesByName);
		
		// Makes sure that caliber is up and running
		this.mockMvc
				.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
				.andExpect(status().isOk());

		// Uses our mocked list to make sure it's received by the service
		this.mockMvc
			.perform(get("/admin/batch/allNames").accept(MediaType.ALL))
			.andExpect(content().json(batchesByNameJson));
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
		Map<String,String> mockMap = new HashMap<String,String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			put("TR-101","a@a.net");
			put("TR-102","a@a.net");
			put("TR-103","a2@a.net");
			}};
			Mockito.when(as.findAllMappings())
			.thenReturn(mockMap);
			
		String mockMapJson = new ObjectMapper().writeValueAsString(mockMap);//What we expect to be in the body of the response
		
		
		this.mockMvc.perform(get("/admin/mappedBatchesClients").accept(MediaType.ALL))
		.andExpect(content().json(mockMapJson));//Makes the request and ensures we receive the proper json
	}

	/**
	 * @throws Exception
	 * @author daniel constantinescu
	 * unit test for mapping batch to client when batchId found
	 */
	@Test
	void testMapBatchToClientSuccess() throws Exception {
		String batchId = "ABC";
		String email = "a@b";
		Mockito.when(as.mapBatchtoClient(batchId, email)).thenReturn(true);
		this.mockMvc
				.perform(put("/admin/mapBatchToClient?batchId=ABC&email=a@b").accept(MediaType.ALL))
				.andExpect(status().isOk());
	}

	/**
	 * @throws Exception
	 * @author daniel constatinescu
	 * unit test for mapping batch to client when batchId not found
	 */
	@Test
	void testMapBatchToClientFail() throws Exception {
		String batchId = "ABC";
		String email = "a@b";
		Mockito.when(as.mapBatchtoClient(batchId, email)).thenReturn(false);
		this.mockMvc
				.perform(put("/admin/mapBatchToClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	void testUnmapBatchFromClientSuccess() throws Exception {
		String batchId = "ABC";
		String email = "a@b";
		Mockito.when(as.unmapBatchFromClient(batchId, email)).thenReturn(true);
		this.mockMvc
				.perform(put("/admin/unmapBatchFromClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void testUnmapBatchFromClientFail() throws Exception {
		String batchId="ABC";
		String email="a@b";
		Mockito.when(as.unmapBatchFromClient(batchId, email)).thenReturn(false);
		this.mockMvc
			.perform(put("/admin/unmapBatchFromClient?batchId=ABC&email=a@b").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isConflict());
	}
	
}


