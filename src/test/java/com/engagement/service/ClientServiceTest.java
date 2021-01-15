package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.Associate;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchOverview;
import com.engagement.model.dto.ClientName;
import com.engagement.model.dto.EmployeeAssignment;
import com.engagement.model.dto.Grade;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.GradeClient;
import com.engagement.repo.caliber.TrainingClient;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class ClientServiceTest {
	
	@Mock
	private GradeClient gc;
	@Mock
	private TrainingClient tc;
	@Mock
	private ClientRepo cr;
	@Mock
	private ClientBatchRepo cbr;

	
	@InjectMocks
	private ClientService cs;

	/**
	 * This tests the getBatchByBatchId method from the client service
	 * @author Kelsey Iafrate
	 */
	@Test 
	void getBatchByBatchIdTest() {
		

		/**
		 * Builds an associate assignment list
		 * @author Kelsey Iafrate
		 */
		final Associate associate = new Associate("test@test", "testId", "testFName", "testLName", null);
		associate.setGrades(associate.getGrades());
		final AssociateAssignment aa = new AssociateAssignment("active", associate, "start Date", "end date", true);
		final List<AssociateAssignment> aAssigns = new ArrayList<>();
		aAssigns.add(aa);

		Batch batch = new Batch("TR-1018", "batchName", "this is a date", "this is an end date", "java", "WVU", "ROCP", 70, 80, new ArrayList<EmployeeAssignment>(), aAssigns, 1);
		
		/**
		 * Builds a grade list to add to the batch
		 * @author Kelsey Iafrate
		 */
		Grade grade = new Grade(1, "date received", 90.1, "testId");
		Grade grade2 = new Grade(2, "date received", 10, "not an id");
		List<Grade> grades = new ArrayList<>();
		grades.add(grade);
		grades.add(grade2);
		
		/**
		 * Tests that a batch's associate's grade list gets properly populated
		 * @author Kelsey Iafrate
		 * 
		 */
		
		Mockito.doReturn(batch).when(tc).getBatchById(batch.getBatchId());
		Mockito.doReturn(grades).when(gc).getGradesByBatchId(batch.getBatchId());
		
		Batch newBatch = cs.getBatchByBatchId(batch.getBatchId());
		
		Associate newAssociate = newBatch.getAssociateAssignments().get(0).getAssociate();
		
		assertEquals(grades.get(0), newAssociate.getGrades().get(0));
		assertEquals(1, newAssociate.getGrades().get(0).getGradeId());
		assertEquals("date received", newAssociate.getGrades().get(0).getDateReceived());
		assertEquals(90.1, newAssociate.getGrades().get(0).getScore());
		assertEquals("testId", newAssociate.getGrades().get(0).getTraineeId());
		assertEquals("testId", newAssociate.getSalesforceId());
		assertEquals("test@test", newAssociate.getEmail());
		assertEquals("testFName", newAssociate.getFirstName());
		assertEquals("testLName", newAssociate.getLastName());
		
		/**
		 * Tests that if no batch is returned, a null is returned
		 * @author Kelsey Iafrate
		 */
		
		Mockito.when(tc.getBatchById("bad batch id")).thenReturn(null);
		
		assertNull(cs.getBatchByBatchId("bad batch id"));
		
		/**
		 * Tests that if a batch doesn't have any grades, a batch is still returned
		 * @author Kelsey Iafrate
		 */
		
		Mockito.when(tc.getBatchById("batch without grades")).thenReturn(batch);
		Mockito.when(gc.getGradesByBatchId("batch without grades")).thenReturn(new ArrayList<Grade>());
		
		assertEquals(batch, cs.getBatchByBatchId("batch without grades"));
		
	}
	
	
	/**
	 * Test that checks if getBatchInfoByEmail returns correct batch overview 
	 * 
	 * @author Matt Hartmann
	 */
	@Test 
	void getBatchInfoByEmailTest() {
		BatchOverview bao = new BatchOverview("Tr-5000", "batchName", "java");
		Client client = new Client(1,"a@a.com", "revature", "5555555");
		ClientBatch clientb = new ClientBatch(1, "Tr-5000", client);
		Batch batch = new Batch("Tr-5000", "batchName", "this is a date", "this is an end date", "java", "WVU", "ROCP", 70, 80, new ArrayList<EmployeeAssignment>(), new ArrayList<AssociateAssignment>() , 1);
		
		
		List<ClientBatch> clist = new ArrayList<>();
		clist.add(clientb);
		
//		Mockito.when(cr.findByEmail("a@a.com")).thenReturn(client);
		Mockito.doReturn(client).when(cr).findByEmail("a@a.com");
		Mockito.doReturn(clist).when(cbr).findByClient(client);
		
		Mockito.doReturn(batch).when(tc).getBatchById("Tr-5000");
		
		List<BatchOverview> results = cs.getBatchInfoByEmail("a@a.com");
		
		assertEquals(bao, results.get(0));
		
	}
	
	@Test 
	void findAllClients() {
	
		/**
		 * Builds an client list
		 * @author Stephen Naugle
		 */
		final Client client = new Client(0, "a@a.net", "revature", "573-555-3535");
		final Client client1 = new Client(1, "b@b.net", "myspace", "123-456-7890");
		final List<Client> expectedList = new ArrayList<>();
		expectedList.add(client);
		expectedList.add(client1);
		
		/**
		 * Tests that a batch's associate's grade list gets properly populated
		 * @author Stephen Naugle
		 */
		
		//Client Service returns list of client & client1
		
		Mockito.when(cs.findAll()).thenReturn(expectedList); 
		assertEquals(expectedList.get(0).toString(), cs.findAll().get(0).toString());
		assertEquals(expectedList.get(1).toString(), cs.findAll().get(1).toString());
		
		/**
		 * Tests that if no batch is returned, a null is returned
		 * @author Stephen Naugle
		 */
		
		Mockito.when(cs.findAll()).thenReturn(expectedList);
		
		assertEquals(expectedList, cs.findAll());
		
	}
	
	@Test 
	void findByEmail() {
	
		/**
		 * Builds an client
		 * @author Stephen Naugle
		 */
		final Client client = new Client(0, "a@a.net", "revature", "573-555-3535");
	
		/**
		 * Tests that a batch's associate's grade list gets properly populated
		 * @author Stephen Naugle
		 */
		
		//Client Service returns list of client & client1
		
		Mockito.when(cs.findByEmail("a@a.net")).thenReturn(client); 
		assertEquals(0, client.getClientId());
		assertEquals("a@a.net", client.getEmail());
		assertEquals("revature", client.getCompanyName());
		assertEquals("573-555-3535", client.getPhoneNumber());
		
		/**
		 * Tests that if no batch is returned, a null is returned
		 * @author Stephen Naugle
		 */
		
		Mockito.when(cs.findByEmail("a@a.net")).thenReturn(client);
		
		assertEquals(client, cs.findByEmail("a@a.net"));
	}
	
	@Test 
	void testAddClient() {
	
		/**
		 * Builds an client
		 * @author Stephen Naugle
		 */
		
		final Client client = new Client(0, "a@a.net", "revature", "573-555-3535");
	
		/**
		 * Tests that the client was saved
		 * @author Stephen Naugle
		 */

		Mockito.when(cr.save(client)).thenReturn(client);
		assertEquals(client, cr.save(client));	
		
	}
	
	@Test
	void testFindClientNames() {
		
		/**
		 * Builds a client list
		 * Tests that the findCLientNames returns the list of Client Names 
		 * when given a list of client
		 * @author Stephen Naugle
		 */
		final ClientName client = new ClientName("revature", "a@a.net");
		final ClientName client1 = new ClientName("myspace", "b@b.net");
		final Client client2 = new Client();
		final Client client3 = new Client();
		client2.setCompanyName("revature");
		client2.setEmail("a@a.net");
		client3.setCompanyName("myspace");
		client3.setEmail("b@b.net");
		
		final List<Client> returnedList = new ArrayList<>();
		final List<ClientName> expectedList = new ArrayList<>();
		expectedList.add(client);
		expectedList.add(client1);
		returnedList.add(client2);
		returnedList.add(client3);
		
		Mockito.when(cr.findAll()).thenReturn(returnedList);
		assertEquals(expectedList, cs.findClientNames());	
		
	}
}
