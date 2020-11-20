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
import com.engagement.model.dto.Associate;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.EmployeeAssignment;
import com.engagement.model.dto.Grade;
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

	
	@InjectMocks
	private ClientService cs;

	/**
	*getBatchByBatchIdTest tests the getBatchByBatchId in the Client Service. 
	*@author Kelsey Iafrate
	*/
	@Test 
	void getBatchByBatchIdTest() {
		/**
		* These lines populate a batch with an associate and a grade
		*/
		final Associate associate = new Associate("test@test", "testId", "testFName", "testLName", null);
		associate.setGrades(associate.getGrades()); //tests the getGrades method in the Associate dto, should return an empty list instead of null. Will get a nullPointerException if it doesn't work
		final AssociateAssignment aa = new AssociateAssignment("active", associate, "start Date", "end date", true);
		final List<AssociateAssignment> aAssigns = new ArrayList<>();
		aAssigns.add(aa);
		
		Batch batch = new Batch("TR-1018", "batchName", "this is a date", "this is an end date", "java", "WVU", "ROCP", 70, 80, new ArrayList<EmployeeAssignment>(), aAssigns, 1);
		
		Grade grade = new Grade(1, "date received", 90.1, "testId");
		Grade grade2 = new Grade(2, "date received", 10, "not an id"); //This is used to check that the if statement in the service will not add a grade to a student's list of grades if it doesn't belong to them
		List<Grade> grades = new ArrayList<>();
		grades.add(grade);
		grades.add(grade2);
		
		/**
		*Tests that the Batch's Associate's list of Grades gets populated correctly
		*@author Kelsey Iafrate
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
		*Checks that if a batch id is passed in that doesn't exist, the service will return a null batch
		*@author Kelsey Iafrate
		*/
		Mockito.when(tc.getBatchById("bad batch id")).thenReturn(null);
		
		assertNull(cs.getBatchByBatchId("bad batch id"));
		
		/**
		*Checks that if a batch's associates don't have grades, the batch will still be returned
		*@author Kelsey Iafrate
		*/
		Mockito.when(tc.getBatchById("batch without grades")).thenReturn(batch);
		Mockito.when(gc.getGradesByBatchId("batch without grades")).thenReturn(new ArrayList<Grade>());
		
		assertEquals(batch, cs.getBatchByBatchId("batch without grades"));
		
	}

}