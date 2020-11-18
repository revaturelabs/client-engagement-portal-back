package com.engagement.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Associate {

	private String salesForceId;
	private String email;
	private String firstName;
	private String lastName;

}
