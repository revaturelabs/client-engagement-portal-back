package com.engagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* 
* @author Matt Hartman
*
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Request {

	public static enum RequestTypes {
		INTERVENTION, TALENT
	}

	public static enum Status {
		PENDING, DONE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requestId;

	@Column(nullable = false)
	private RequestTypes requestType;

	@Column(nullable = false)
	private Status status;

	private String message;

	private int clientId;

}
