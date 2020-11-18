package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.client.BatchClient;
import com.engagement.model.Batch;

@RestController
@RequestMapping("/batch")
public class BatchController {
	
	@Autowired
	BatchClient bc;
	
	@GetMapping("/")
	public List<Batch> getBatches(){
		return bc.getBatches();
	}
}
