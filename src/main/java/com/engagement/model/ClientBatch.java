package com.engagement.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* ClientBatch is a go-between class to record the relationship 
* between a client and one of their batches.
* 
* @author Matt Hartman
*
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientBatchId;
	
	private String batchId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	private Client client;

}