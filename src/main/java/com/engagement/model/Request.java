package com.engagement.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@Enumerated(EnumType.ORDINAL)
	private RequestTypes requestType;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	private String message;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateCreated;

}
