package com.engagement.model.dto;

import java.util.List;

import com.engagement.model.dto.Grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
	
	private String email;
	private String salesfoceId;
	private String firstName;
	private String lastName;
	private List<Grade> grades;

}
