package com.engagement.model.dto;

import java.util.List;

import com.engagement.model.dto.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * An associate is a trainee. 
 * This is used in the Batch model to show the client information about the trainees of a batch
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
	
	private String email;
	private String salesfoceId;//also called traineeId in other places
	private String firstName;
	private String lastName;
	private List<Grade> grades;

}
