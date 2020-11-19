package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * An AssociateAssignment has a trainee and more information about their position in the organization.
 * This is used in the batch model as a collection of a batch's trainees. 
 *
 * @author Kelsy Iafrate
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
