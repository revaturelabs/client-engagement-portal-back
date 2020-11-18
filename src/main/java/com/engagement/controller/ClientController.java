package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Client;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService cs;
	
	@GetMapping
	public List<Client> getAll() {
		return cs.getAll();
	}
	
	@PostMapping
	public String insert(@RequestBody Client c) {
		cs.save(c);
		return "inserted";
	}
	
	@GetMapping("/id")
	public List<Client> findById(@RequestParam int id) {
		return cs.findById(id);
	}
}
