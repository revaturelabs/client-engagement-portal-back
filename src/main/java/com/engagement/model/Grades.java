package com.engagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grades {

	private String assessmentType;
	private double score;
	private String traneeId;
	private int week;
	private int weight;
}
