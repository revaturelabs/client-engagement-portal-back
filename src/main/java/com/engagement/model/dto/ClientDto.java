package com.engagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

	private int clientId;
	private String email;
	private String companyName;
	private String phoneNumber;
	private List<ClientBatch> clientBatches;
	
}
