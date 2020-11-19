package com.engagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.dto.Associate;
import com.engagement.model.dto.AssociateAssignment;
import com.engagement.model.dto.Batch;
import com.engagement.model.dto.Grade;
import com.engagement.repo.caliber.BatchClient;
import com.engagement.repo.caliber.GradeClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientService {

	@Autowired
	private BatchClient bc;

	@Autowired
	private GradeClient gc;

	public Batch getBatchByBatchId(String batchId) {

		Batch b = bc.getBatchById(batchId);
		Grade[] grades = gc.getGradesByBatchId(batchId);
		for (Grade grade: grades) {
			for (AssociateAssignment a : b.getAssociateAssignments()) {
				if(grade.getTraineeId() == a.getAssociate().getSalesfoceId()) {
					a.getAssociate().getGrades().add(grade);
					break;
				}
			}
		}
		return b;
	}
}
