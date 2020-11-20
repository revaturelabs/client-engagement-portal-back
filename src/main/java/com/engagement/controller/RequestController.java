package com.engagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Request;
import com.engagement.service.RequestService;

@RestController
@RequestMapping("/intervention")
public class RequestController {

	@Autowired
	private RequestService rs;

//	@GetMapping("/interventions")
	@GetMapping
	@ResponseBody
	public List<Request> getAllInterventions() {
		return rs.findAll();
	}

	@GetMapping("/intervention/{id}")
	public Request getInterventionById(@PathVariable int id) {
		return rs.findByRequestId(id);
	}

//	@PostMapping("/interventions")
	@PostMapping
	@ResponseBody
	public String saveIntervention(@RequestBody Request request) {
		rs.addIntervention(request);
		return "Intervention request added";

	}
}
