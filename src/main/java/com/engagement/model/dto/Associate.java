package com.engagement.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 
 * An associate is a trainee. 
 * This is used in the AssociatAssignment where more information about the trainee will be given.
 * 
 * @author Kelsey Iafrate
 */

@Data
@AllArgsConstructor
public class Associate {

	private String email;
	private String salesforceId;//also called traineeId in other places
	private String firstName;
	private String lastName;
	private List<Grade> grades;
	
	/**
	 * Needed because otherwise we get null if the list of grades was not initialized
	 * @return either the list of grades or an empty list
	 * @author Kelsey Iafrate
	 */
	public List<Grade> getGrades(){
		if(this.grades == null) {
			return new ArrayList<>();
		}
		return grades;
	}

}