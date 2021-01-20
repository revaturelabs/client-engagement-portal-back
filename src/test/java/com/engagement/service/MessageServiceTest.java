package com.engagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageAdminDTO;
import com.engagement.model.dto.MessageClientDTO;
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
	private MessageService messageService;
	
	Client client0 = new Client(0, "client0@a", "walmart", "573-555-3535");
	Admin admin0 = new Admin(0,"admin0@b","firstnameadmin0","lastnameadmin0");
	
	MessageAdminDTO messageAdminDTO = new MessageAdminDTO(client0.getEmail(), admin0.getEmail(), "title", "Hello from MessageAdminDTO");
	MessageClientDTO messageClientDTO = new MessageClientDTO(admin0.getEmail(), client0.getEmail(), "title", "Hello from MessageClientDTO");
	
	Message testMessage = new Message(0, true, admin0, client0, "Test message", null, false, "Test title");
	Message testMessage1 = new Message(1, true, admin0, client0, "Test message1", null, false, "Test1 title");
	
	/**
	 * This tests verifies that the messageService.adminAddMessage() method calls the save method.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testAdminAddMessage() {
		// Create a message
		Admin admin = adminRepo.findByEmail(messageAdminDTO.getAdminEmail());
		Client client = clientRepo.findByEmail(messageAdminDTO.getClientEmail());
		Message mockMessageAdmin = new Message(0, true, admin, client, messageAdminDTO.getMessage(), null, false, messageAdminDTO.getTitle());
		Mockito.when(messageRepo.save(ArgumentMatchers.any(Message.class))).thenReturn(mockMessageAdmin);
		
		// Save Message
		Message newAdminMessage = messageService.adminAddMessage(messageAdminDTO);
		
		// Verify save
		assertEquals("Hello from MessageAdminDTO", newAdminMessage.getMessage());
		
	}
	
	
	/**
	 * This tests verifies that the messageService.clientAddMessage() method calls the save method.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testClientAddMessage() {
		// Create a message
		Admin admin = adminRepo.findByEmail(messageClientDTO.getAdminEmail());
		Client client = clientRepo.findByEmail(messageClientDTO.getClientEmail());
		Message mockClientMessage = new Message(0, false, admin, client, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		Mockito.when(messageRepo.save(ArgumentMatchers.any(Message.class))).thenReturn(mockClientMessage);
		
		// Save Message
		Message newClientMessage = messageService.clientAddMessage(messageClientDTO);
		
		// Verify save
		assertEquals("Hello from MessageClientDTO", newClientMessage.getMessage());
	}
	
	
	/**
	 * This tests verifies that the findAll() method has been called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testGetMessages() {
		List<Message> messages = new ArrayList<>();
		messages.add(testMessage);
		messages.add(testMessage1);
		Mockito.when(messageRepo.findAll()).thenReturn(messages);
		assertNotNull(messageService.getMessages());
		assertTrue(messages.size() == 2);
		
	}
	
	
	/**
	 * This tests verifies findById(int id) method has been called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testGetMessageById() {
		Mockito.when(messageRepo.findById(0)).thenReturn(testMessage);
		Message foundMessage = messageService.getMessageById(0);
		assertEquals("Test message", foundMessage.getMessage());
	}
	
	
	/**
	 * This tests verifies delete method is called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testDeleteMessage() {
		// if message exists
		Mockito.when(messageRepo.findById(0)).thenReturn(testMessage);
		assertEquals(messageService.deleteMessage(0), "Message id: " + 0 + " has successfully deleted");
		// if message doesn't exist
		Mockito.when(messageRepo.findById(1)).thenReturn(null);
		assertEquals("Message NOT found", messageService.deleteMessage(1));
	}
	
	
	/**
	 * This tests verifies findByMessage method is called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testFindByMessage() {
		Mockito.when(messageRepo.findByMessage("Test message")).thenReturn(testMessage);
		Message foundMessage = messageService.findByMessage("Test message");
		assertEquals(testMessage.getMessage(), foundMessage.getMessage());
	}
	
	
	/**
	 * This tests verifies messageRepo.findByclientId(int) and clientRepo.findById(int) methods are called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testFindByClientId() {
		List<Message> messages = new ArrayList<>();
		Message mockClientMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messages.add(mockClientMessage);
		Mockito.when(clientRepo.findById(0)).thenReturn(client0);
		Mockito.when(messageRepo.findByclientId(client0)).thenReturn(messages);
		List<Message> foundMessages = messageService.findByClientId(0);
		assertTrue(foundMessages.get(0) == mockClientMessage);
	}
	
	
	/**
	 * This tests verifies adminRepo.findByEmail(int) and messageRepo.findByadminId(int) methods are called.
	 * 
	 * @author Takia Ross
	 */
	@Test
	void testFindByAdminId() {
		List<Message> messages = new ArrayList<>();
		Message mockAdminMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messages.add(mockAdminMessage);
		Mockito.when(adminRepo.findByAdminId(0)).thenReturn(admin0);
		Mockito.when(messageRepo.findByadminId(admin0)).thenReturn(messages);
		List<Message> foundMessages = messageService.findByAdminId(0);
		assertTrue(foundMessages.get(0) == mockAdminMessage);
	}
	
	/**
	 * This tests verifies messageRepo.findByclientId(int) and clientRepo.findByEmail(String) methods are called.
	 * @author Tianyuan Deng
	 */
	@Test
	public void testFindByClientEmail() {
		List<Message> messages = new ArrayList<>();
		Message mockClientMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messages.add(mockClientMessage);
		Mockito.when(clientRepo.findByEmail("client0@a")).thenReturn(client0);
		Mockito.when(messageRepo.findByclientId(client0)).thenReturn(messages);
		List<Message> foundMessages = messageService.findByClientEmail("client0@a");
		assertTrue(foundMessages.get(0) == mockClientMessage);
	}
	
	
	/**
	 * This tests verifies messageRepo.findByadminId(int) and adminRepo.findByEmail(String) methods are called.
	 * @author Tianyuan Deng
	 */
	@Test
	void testFindByAdminEmail() {
		List<Message> messages = new ArrayList<>();
		Message mockAdminMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messages.add(mockAdminMessage);
		Mockito.when(adminRepo.findByEmail("admin0@b")).thenReturn(admin0);
		Mockito.when(messageRepo.findByadminId(admin0)).thenReturn(messages);
		List<Message> foundMessages = messageService.findByAdminEmail("admin0@b");
		assertTrue(foundMessages.get(0) == mockAdminMessage);
	}
	
	
	/**
	 * This tests verifies messageRepo.findByadminId(), messageRepo.findByclientId(), adminRepo.findByEmail() and clientRepo.findByEmailL() methods are called.
	 * @author Tianyuan Deng
	 */
	@Test
	void testFindMessageByEmail() {
		List<Message> messages = new ArrayList<>();
//		case 1: Find message by clientEmail 
		Message mockClientMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messages.add(mockClientMessage);
		Mockito.when(clientRepo.findByEmail("client0@a")).thenReturn(client0);
		Mockito.when(messageRepo.findByclientId(client0)).thenReturn(messages);
		List<Message> foundMessagesClient = messageService.findByClientEmail("client0@a");
		assertTrue(foundMessagesClient.get(0) == mockClientMessage);
//		case 2: Find message by adminEmail
		List<Message> messagesAdmin = new ArrayList<>();
		Message mockAdminMessage = new Message(0, false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
		messagesAdmin.add(mockAdminMessage);
		Mockito.when(adminRepo.findByEmail("admin0@b")).thenReturn(admin0);
		Mockito.when(messageRepo.findByadminId(admin0)).thenReturn(messagesAdmin);
		List<Message> foundMessagesAdmin = messageService.findByAdminEmail("admin0@b");
		assertTrue(foundMessagesAdmin.get(0) == mockAdminMessage);
	}
	
	
	
}