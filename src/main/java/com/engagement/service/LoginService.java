package com.engagement.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientRepo;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	private ClientRepo cr;
	private AdminRepo ar;
	
	@Autowired
	public LoginService(ClientRepo cr, AdminRepo ar) {
		this.cr = cr;
		this.ar = ar;	
	}
	
	public Client getClient(String email){
		return cr.findByEmail(email);	
	}
	
	public Admin getAdmin(String email){
		return ar.findByEmail(email);
	}
}
