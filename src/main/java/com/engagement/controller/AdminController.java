package com.engagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engagement.model.Admin;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService as;
	
	@PostMapping("/new")
	public Admin create(@RequestBody Admin a) {
		as.create(a);
	}
	
	@PostMapping("/login")
	public Admin login(@RequestParam String username, @RequestParam String password) {
		as.login(username,password);
	}
	
	@PostMapping("/update")
	public Admin update(@RequestBody Admin a) {
		as.update(a);
	}
	
	@PostMapping("/delete")
	public String delete(Admin a) {
		as.delete(a);
	}

}
