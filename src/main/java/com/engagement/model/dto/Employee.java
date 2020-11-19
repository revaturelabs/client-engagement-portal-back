package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * An employee is someone related to a batch that is not a trainee
 * For instance, every batch has a trainer employee
 * This is used in the EmployeeAssignment model where more information about the employee will be given.
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	private String email;
	private String firstName;
	private String lastName;

}
