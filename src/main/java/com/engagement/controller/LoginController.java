package com.engagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.service.LoginService;

@Controller
@CrossOrigin
@RequestMapping("/api/login")
public class LoginController {

	public LoginService ls;
	
	@Autowired
	public LoginController(LoginService ls) {
		super();
		this.ls = ls;
	}

	@PostMapping("/admin")
	public @ResponseBody Admin loginadmin(@RequestBody String email) {
		return ls.getAdmin(email);
	}
	
	@PostMapping("/client")
	public @ResponseBody Client loginclient(@RequestBody String email) {
		return ls.getClient(email);
	}
	
	



}
