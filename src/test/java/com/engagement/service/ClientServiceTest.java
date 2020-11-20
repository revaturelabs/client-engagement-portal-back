package com.engagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.controller.ClientController;
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
public class ClientServiceTest {
	
	@MockBean
	private GradeClient gc;
	@MockBean
	private TrainingClient tc;
	@MockBean
	private ClientRepo cr;

	
	@InjectMocks
	private ClientService cs;
	
	@Before
	public void setUp() {
		
	}

	@Test 
	void getBatchByBatchIdTest() {
		final Associate associate = new Associate("test@test", "testId", "testFName", "testLName", new ArrayList<Grade>());
		final AssociateAssignment aa = new AssociateAssignment("active", associate, "start Date", "end date", true);
		final List<AssociateAssignment> aAssigns = new ArrayList<>();
		aAssigns.add(aa);
		Batch batch = new Batch("TR-1018", "batchName", "this is a date", "this is an end date", "java", "WVU", "ROCP", 70, 80, new ArrayList<EmployeeAssignment>(), aAssigns, 1);
		Grade grade = new Grade(1, "date received", 90.1, "testId");
		List<Grade> grades = new ArrayList<>();
		grades.add(grade);
		
		Mockito.when(tc.getBatchById(batch.getBatchId())).thenReturn(batch);
		Mockito.when(gc.getGradesByBatchId(batch.getBatchId())).thenReturn(grades);
		
		Batch newBatch = cs.getBatchByBatchId(batch.getBatchId());
		
		assertEquals(grades, newBatch.getAssociateAssignments().get(0).getAssociate().getGrades());
		
	}

}
