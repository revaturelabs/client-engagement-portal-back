package com.engagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engagement.model.Admin;
import com.engagement.repo.AdminRepo;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepo   adminRepository;
	
	public Admin findByAdminId(Integer id) {
		return adminRepository.findByAdminId(id);
		
	}
	
	public Admin findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}
	
	public boolean register(Admin admin) {
	  boolean ret=false;
		if ( adminRepository.save(admin) != null)
		  ret= true;
	  else
		  ret=false;
	  
	  return ret;
				  
	   
	}
	
	public Admin update(Admin admin) {
		boolean ret=false;
	
		return adminRepository.save(admin);
	}
	
	public boolean delete(Integer id) {
	  
	try {
		
	 adminRepository.deleteById(id);
	}
	catch (Exception e) {
		e.fillInStackTrace();
	}
	 return true;
	
	}
	
	//TODO:  need further authentication implemented
	public boolean login(String user, String pass) {
		boolean ret=false;
		if (adminRepository.findByUsernameAndPassword (user,pass) != null )
			ret=true;
		else
			ret=false;
		
		return ret;
	}


public boolean delete(String username) {
	boolean ret=false;
	if (adminRepository.deleteByUsername(username))
		ret=true;
		else
			ret=false;
			
	return ret;
		
}
}
	

	

