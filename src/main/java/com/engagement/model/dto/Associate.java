package com.engagement.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * An associate is a trainee. 
 * This is used in the AssociatAssignment where more information about the trainee will be given.
 * 
 * @author Kelsey Iafrate
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {

	private String email;
	private String salesforceId;//also called traineeId in other places
	private String firstName;
	private String lastName;
	private List<Grade> grades;

}