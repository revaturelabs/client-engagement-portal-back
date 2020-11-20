package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * An EmployeeAssignment has an employee and declares what role that employee has, e.g. Trainer.
 * This will be used in a Batch object as a list of employees and their roles
 * 
 * @author  Kelsey Iafrate
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAssignment {
	
	private String role;
	private Employee employee;

}