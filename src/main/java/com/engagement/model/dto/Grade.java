package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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