package com.engagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.dto.Batch;
import com.engagement.service.ClientService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientController {
	
	@Autowired
	ClientService cs;
	
	@GetMapping("/batch/{batchId}")
	public Batch getBatchById(String batchId) {
		return cs.getBatchByBatchId(batchId);
	}

}
