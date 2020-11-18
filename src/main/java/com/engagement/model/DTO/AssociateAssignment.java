package com.engagement.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociateAssignment {
	
	private String trainingStatus;
	private Associate associate;
	private String startDate;
	private String endDate;
	private boolean active;

}
