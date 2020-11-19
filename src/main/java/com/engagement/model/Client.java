package com.engagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientId;

	private String email;

	private String companyName;

	private String phoneNumber;

	@OneToMany(mappedBy = "client")
	private List<ClientBatch> clientBatches;



}
