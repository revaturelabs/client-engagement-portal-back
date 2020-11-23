package com.engagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.service.RequestService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RequestController.class)

public class RequestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RequestService rs;

	@InjectMocks
	private RequestController rc;

	private String testRequestJson1 = "{\"requestId\":1, \"requestType\":\"TALENT\", \"status\":\"DONE\",\"message\":\"test comment2\",\"clientId\":2}";

	private Request testRequest0 = new Request(0, RequestTypes.INTERVENTION, Status.PENDING, "test comment", 1);
	private Request testRequest1 = new Request(1, RequestTypes.TALENT, Status.DONE, "test comment2", 2);

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(rc).build();

	}

	/**
	 * This tests that the getAllInterventions method returns a the correct status
	 * code and returns a list of interventions.
	 * 
	 * @author Robert Porto
	 */
	@Test
	public void getAllInterventionsTest() throws Exception {
		List<Request> testRequests = new ArrayList<>();
		testRequests.add(testRequest0);
		testRequests.add(testRequest1);
		Mockito.when(rs.findAll()).thenReturn(testRequests);
		this.mockMvc.perform(get("/intervention/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())

				.andExpect(jsonPath("$[*].requestId").value(Matchers.containsInAnyOrder(0, 1)))
				.andExpect(jsonPath("$[*].requestType").value(Matchers.containsInAnyOrder("INTERVENTION", "TALENT")))
				.andExpect(jsonPath("$[*].status").value(Matchers.containsInAnyOrder("PENDING", "DONE"))).andExpect(
						jsonPath("$[*].message").value(Matchers.containsInAnyOrder("test comment", "test comment2")));
	}

	/**
	 * This tests that the getInterventionById method returns the correct status
	 * code and returns an intervention that corresponds to the given id.
	 * 
	 * @author Robert Porto
	 */
	@Test
	public void getInterventionByIdTest() throws Exception {
		Mockito.when(rs.findByRequestId(0)).thenReturn(testRequest0);
		this.mockMvc.perform(get("/intervention/0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.requestId").value(0)).andExpect(jsonPath("$.message").value("test comment"));

	}

	/**
	 * This tests that the save method returns the correct status given that the
	 * request service save method returns true
	 * 
	 * @author Robert Porto
	 */

	@Test
	public void saveInterventionSuccessTest() throws Exception {
		Mockito.when(rs.save(testRequest1)).thenReturn(true);
		this.mockMvc.perform(post("/intervention/").contentType(MediaType.APPLICATION_JSON).content(testRequestJson1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

	}

	/**
	 * This tests that the save method returns the correct status given that the
	 * request service save method returns false
	 * 
	 * @author Robert Porto
	 */

	@Test
	public void saveInterventionFailTest() throws Exception {
		Mockito.when(rs.save(testRequest1)).thenReturn(false);
		this.mockMvc.perform(post("/intervention/").contentType(MediaType.APPLICATION_JSON).content(testRequestJson1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

	}

}
