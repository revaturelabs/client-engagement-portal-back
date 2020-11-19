package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * A grade is a score a trainee received during training.
 * This will be used in a Trainee object as a list of the grades they have received during training.
 *
 * @author Kelsy Iafrate
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

	private int gradeId;
	private String dateReceived;
	private double score;
	private String traineeId;
}
