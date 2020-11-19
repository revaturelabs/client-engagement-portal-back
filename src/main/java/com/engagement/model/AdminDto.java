package com.engagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

	private int adminId;
	private String email;
	private String firstName;
	private String lastName;
	
}
