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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchOverview;
import com.engagement.model.dto.ClientName;
import com.engagement.model.dto.EmployeeAssignment;
import com.engagement.service.ClientService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ClientController.class)
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService cs;
	
	@InjectMocks
	private ClientController cc;
	
	private String mockClientJson = "{\"clientId\":0,\"email\":\"a@a.net\", \"companyName\":\"revature\", \"phoneNumber\":\"573-555-3535\"}";
	Client client0 = new Client(0, "a@a.net", "revature", "573-555-3535");
	Client client1 = new Client(1, "a@a1.net", "myspace", "573-343-1334");
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(cc).build();
	}
	
	@Test
	void testCreateNewClientSuccess() throws Exception {
		Mockito.when(cs.save(client0)).thenReturn(true);
		this.mockMvc
			.perform(post("/client/").contentType(MediaType.APPLICATION_JSON)
									.content(mockClientJson)
									.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	void testCreateNewClientFail() throws Exception {
		Mockito.when(cs.save(client0)).thenReturn(false);
		this.mockMvc
			.perform(post("/client/").contentType(MediaType.APPLICATION_JSON)
										.content(mockClientJson)
										.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isConflict());
	}
	
	
	@Test
	void findAllClient() throws Exception {
		List<Client> expectedList = new ArrayList<>();
		expectedList.add(client0);
		expectedList.add(client1);
		Mockito.when(cs.findAll()).thenReturn(expectedList); //Controller service returns list of client0 and client1
		this.mockMvc
		.perform(get("/client/")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()) //expect a status of ok
		//Expect to get back json with both clients in expectedList
		.andExpect(jsonPath("$[*].clientId").value(Matchers.containsInAnyOrder(0, 1)))
		.andExpect(jsonPath("$[*].email").value(Matchers.containsInAnyOrder("a@a.net", "a@a1.net")))
		.andExpect(jsonPath("$[*].companyName").value(Matchers.containsInAnyOrder("revature", "myspace")))
		.andExpect(jsonPath("$[*].phoneNumber").value(Matchers.containsInAnyOrder("573-555-3535", "573-343-1334")));
	}
	
	@Test
	void findByEmail() throws Exception {
		Mockito.when(cs.findByEmail("a@a.net")).thenReturn(client0); //Controller service returns client 0 when given a@a.net
		this.mockMvc
		.perform(get("/client/email/a@a.net")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()) //expect a status of ok
		//Expect to get back json with variables set in client0
		.andExpect(jsonPath("$.clientId").value(0))
		.andExpect(jsonPath("$.email").value("a@a.net"))
		.andExpect(jsonPath("$.companyName").value("revature"))
		.andExpect(jsonPath("$.phoneNumber").value("573-555-3535"));
	}
	
	@Test
	void getBatchById() throws Exception {
		Batch batch = new Batch("TR-1018", "batchName", "this is a date", "this is an end date", "java", "WVU", "ROCP", 70, 80, new ArrayList<EmployeeAssignment>(), new ArrayList<AssociateAssignment>(), 1);
		Mockito.when(cs.getBatchByBatchId("TR-1018")).thenReturn(batch);
		this.mockMvc
		.perform(get("/client/batch/TR-1018"))
		.andExpect(jsonPath("$.batchId").value("TR-1018"))
		.andExpect(jsonPath("$.name").value("batchName"))
		.andExpect(jsonPath("$.startDate").value("this is a date"))
		.andExpect(jsonPath("$.endDate").value("this is an end date"))
		.andExpect(jsonPath("$.skill").value("java"))
		.andExpect(jsonPath("$.location").value("WVU"))
		.andExpect(jsonPath("$.type").value("ROCP"))
		.andExpect(jsonPath("$.goodGrade").value(70))
		.andExpect(jsonPath("$.passingGrade").value(80))
		.andExpect(jsonPath("$.currentWeek").value(1))
		.andExpect(jsonPath("$.employeeAssignments").isEmpty())
		.andExpect(jsonPath("$.associateAssignments").isEmpty());
	}
	
	@Test
	void findClientNames() throws Exception {
		List<ClientName> expectedList = new ArrayList<>();
		expectedList.add(new ClientName("revature", "0"));
		expectedList.add(new ClientName("myspace", "1"));
		Mockito.when(cs.findClientNames()).thenReturn(expectedList);
		this.mockMvc
		.perform(get("/client/clientnames")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()) //expect a status of ok
		.andExpect(jsonPath("$[*].clientId").value(Matchers.containsInAnyOrder("0", "1")))
		.andExpect(jsonPath("$[*].companyName").value(Matchers.containsInAnyOrder("revature", "myspace")));
	}
	
	
	/**
	 * Test that checks if getBatchOverviewbyClient method on controller layer is functioning properly
	 * 
	 * @author Matt Hartmann
	 */
	@Test
	void getOverviewbyClient() throws Exception {
		BatchOverview bao = new BatchOverview("Tr-5000", "batchName", "java");
		Client client = new Client(1,"a@a", "revature", "5555555");
		List<BatchOverview> expectedList = new ArrayList<>();
		expectedList.add(bao);
		System.out.println(bao);
		Mockito.when(cs.getBatchInfoByEmail("a@a")).thenReturn(expectedList);
		this.mockMvc
		.perform(get("/client/batch/email/a@a").accept("*/*")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()) //expect a status of ok
		.andExpect(jsonPath("$[*].batchId").value(Matchers.containsInAnyOrder("Tr-5000")))
		.andExpect(jsonPath("$[*].name").value(Matchers.containsInAnyOrder("batchName")))
		.andExpect(jsonPath("$[*].skill").value(Matchers.containsInAnyOrder("java")));
		
	}
}
