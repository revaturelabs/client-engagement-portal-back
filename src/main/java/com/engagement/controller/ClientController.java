package com.engagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Client;
import com.engagement.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService cs;
	
	@GetMapping
	@ResponseBody
	public List<Client> findAll() {
		return cs.findAll();
	}
	
	@PostMapping
	@ResponseBody
	public Client save(@RequestBody Client c) {
		return cs.save(c);
	}
	
	@GetMapping("/id")
	@ResponseBody
	public Client findById(@RequestParam int id) {
		Optional<Client> optClient = cs.findById(id);
		if(optClient.isPresent()) {
			return optClient.get();
		}
		return null;
	}
	
	@GetMapping("/email")
	@ResponseBody
	public Client findByEmail(@RequestParam String email) {
		return cs.findByEmail(email);
	}
}
