package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * An AssociateAssignment has a trainee and more information 
 *
 */

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
