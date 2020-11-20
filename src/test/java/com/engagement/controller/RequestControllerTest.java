package com.engagement.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.model.Request;
import com.engagement.model.Request.RequestTypes;
import com.engagement.model.Request.Status;
import com.engagement.service.RequestService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RequestController.class)
public class RequestControllerTest {

	@MockBean
	private RequestService rs;

//	@MockBean
//	private AdminService as;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private RequestController rc;

	private Request testRequest;
	private List<Request> testRequests;

	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(rc).build();
		testRequest = new Request(0, RequestTypes.INTERVENTION, Status.PENDING, "test comment", 1);
		testRequests.add(testRequest);
	}

	@Test
	public void getAllInterventionsTest() throws Exception {
		Mockito.when(rs.findAll()).thenReturn(testRequests);
		assertNotNull(rc.getAllInterventions());
//		this.mockMvc.perform(get("/interventions")).andExpect(jsonPath("$[*].message").value("test comment"));

	}

//	@Test
//	public void getInterventionByIdTest() {
//		assertNotNull(rc.getInterventionById(1));
//
//	}
}
