package com.engagement.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.Message.haveBatch;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.MessageRepo;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
class MessageServiceTest {
	public LocalDateTime date = LocalDateTime.now();
	
	@Mock
	private MessageRepo messageRepo;
	
	@Mock
	private AdminRepo adminRepo;
	
	@Mock
	private ClientRepo clientRepo;
	
	@InjectMocks
	private MessageService messageSevice;
	
	@Mock
	private Message message;
	
	Client client0 = new Client(0, "c0@a.net", "walmart", "573-555-3535");
	
	Client client1 = new Client(1, "c1@a.net", "bankone", "573-555-4535");

	Admin admin1= new Admin(1,"a@b","fstubtest1","lstubtest1");
	
	Admin admin2= new Admin(3,"a@b","fstubtest1","lstubtest1");
	
	@Test
	void testAddMessages() {
		// Create a message
		Message mockMessage0 = new Message(0, false, null, client0, "Hello", date, false, haveBatch.NOBATCH);
		Mockito.when(messageRepo.save(mockMessage0)).thenReturn(mockMessage0);
		
		//Message mockMessage1 = new Message(1, false, null, client1, "Hello, World", date, false, haveBatch.NOBATCH);
		
		// Save Message
	}
	
	
	
	

}
