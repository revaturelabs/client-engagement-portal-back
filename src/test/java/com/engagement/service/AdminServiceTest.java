package com.engagement.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.engagement.repo.AdminRepo;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AdminServiceTest {

@Mock	
AdminRepo adminRepository;
	
	
	

	void getAdminbyEmail() {
	when(adminRepository.findByAdminId(an

	
	}
	
}
