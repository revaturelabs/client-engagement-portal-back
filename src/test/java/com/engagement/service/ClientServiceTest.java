package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.engagement.model.Client;
import com.engagement.model.ClientBatch;
import com.engagement.model.dto.Associate;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.BatchOverview;
import com.engagement.model.dto.EmployeeAssignment;
import com.engagement.model.dto.Grade;
import com.engagement.repo.ClientBatchRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.caliber.GradeClient;
import com.engagement.repo.caliber.TrainingClient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClientServiceTest {
	
	@MockBean
	private GradeClient gc;
	@MockBean
	private TrainingClient tc;
	@MockBean
	private ClientRepo cr;
	@MockBean
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
		
		Mockito.when(tc.getBatchById(batch.getBatchId())).thenReturn(batch);
		Mockito.when(gc.getGradesByBatchId(batch.getBatchId())).thenReturn(grades);
		
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
		
		Mockito.when(cr.findByEmail("a@a.com")).thenReturn(client);
		Mockito.when(cbr.findByClient(client)).thenReturn(clist);
		
		Mockito.when(tc.getBatchById("Tr-5000")).thenReturn(batch);
		
		List<BatchOverview> results = cs.getBatchInfoByEmail("a@a.com");
		
		assertEquals(bao, results.get(0));
		
	}

}
