package com.engagement.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.engagement.model.Admin;
import com.engagement.repo.AdminRepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AdminServiceTest {

	
	@Mock	
	AdminRepo arMock=mock(AdminRepo.class);
	
	@InjectMocks
	AdminService adminService;
	
	@Test
	void testfindAll() {
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(12,"a@b","fstubtest2","lstubtest2");
		
		List<Admin> admins = new ArrayList<Admin>();
		
		admins.add(admin1);
		admins.add(admin2);
		
		when(arMock.findAll()).thenReturn(admins);
		
		assertEquals(admins,adminService.findAll(), "findall shoud return a list of admins");

	}
	@Test
	void testfindAllShoudhavesavedUsers() {
		
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(2,"a@b","fstubtest2","lstubtest2");
		Admin admin3= new Admin(3,"a@b","fstubtest3","lstubtest3");
		
		List<Admin> admins = new ArrayList<Admin>();
		List<Admin> admins2 = new ArrayList<Admin>();
		
		admins.add(admin1);
		admins.add(admin2);
		admins2.add(admin1);
		admins2.add(admin2);
		admins2.add(admin3);
		
		when(arMock.findAll()).thenReturn(admins);
		
		when(arMock.findAll()).thenReturn(admins);
		
		assertNotEquals(admins2,adminService.findAll(), "findall shoud return a list of admins");
	
	}	
	
	@Test
	void testFindByEmailExist() {
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(2,"a@b","fstubtest2","lstubtest2");
		
		when(arMock.findByEmail(admin2.getEmail())).thenReturn(admin2);
		assertEquals(admin2, adminService.findByEmail(admin2.getEmail()),"should return admin");
	}
	@Test
	void testFindByEmailNotExist() {
		Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
		Admin admin2= new Admin(2,"a@b","fstubtest2","lstubtest2");
		
		when(arMock.findByEmail(admin2.getEmail())).thenReturn(admin2);
		assertNotEquals(admin1, adminService.findByEmail(admin2.getEmail()),"should't be equal");
	}
}
