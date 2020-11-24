package com.engagement.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

	public enum RequestTypes {
		INTERVENTION, TALENT
	}

	public enum Status {
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;

	private LocalDateTime dateCreated;

}
