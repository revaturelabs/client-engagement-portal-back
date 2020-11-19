package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * A grade is a score a trainee received during training.
 * This will be used in a Trainee object as a list of the grades they have received during training.
 *
 * @author Kelsey Iafrate
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

	private String assessmentType;
	private double score;
	private String traineeId;
	private int week;
	private int weight;
}
