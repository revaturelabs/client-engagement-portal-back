package com.engagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientBatch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientBatchId;
	
	
	private int batchId;
	
	@ManyToOne
	@JoinColumn
	private Client client;

}
