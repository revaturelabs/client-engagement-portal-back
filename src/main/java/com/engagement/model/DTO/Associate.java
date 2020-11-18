package com.engagement.model.DTO;

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
	private Grade grades;

}
