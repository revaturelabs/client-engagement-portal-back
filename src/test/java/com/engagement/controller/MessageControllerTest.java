/**
 * 
 */
package com.engagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.translation.messages_bg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageAdminDTO;
import com.engagement.model.dto.MessageClientDTO;
import com.engagement.repo.MessageRepo;
import com.engagement.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Takia Ross
 *
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MessageController.class)

class MessageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MessageService messageService;
	
	@InjectMocks
	private MessageController messageController;
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();

	}
	
	
	
	/*
     * Method that converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	Client client0 = new Client(0, "client0@a", "walmart", "573-555-3535");
	Admin admin0= new Admin(0,"admin0@b","firstnameadmin0","lastnameadmin0");
	
	MessageAdminDTO messageAdminDTO = new MessageAdminDTO(client0.getEmail(), admin0.getEmail(),"title","Hello from MessageAdminDTO");
	MessageClientDTO messageClientDTO = new MessageClientDTO(admin0.getEmail(), client0.getEmail(),"title","Hello from MessageClientDTO"
			+ "");
	
	Message mockAdminMessage = new Message(0,true, admin0, client0, messageAdminDTO.getMessage(), null, false, messageAdminDTO.getTitle());
	Message mockClientMessage = new Message(0,false, admin0, client0, messageClientDTO.getMessage(), null, false, messageClientDTO.getTitle());
	Message testMessage = new Message(0, true, admin0, client0, "Test message", null, false, "test title");
	
	List<Message> messages = new ArrayList<>();
	
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#findAll()}.
	 * @throws Exception 
	 */
	@Test
	void testFindAll() throws Exception {
		messages.add(mockAdminMessage);
		messages.add(mockClientMessage);
		Mockito.when(messageService.getMessages()).thenReturn(messages);
		this.mockMvc.perform(get("/msg"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andReturn();
	}

	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getMessageById(int)}.
	 * @throws Exception 
	 */
	@Test
	void testGetMessageById() throws Exception {
		Mockito.when(messageService.getMessageById(0)).thenReturn(testMessage);
		this.mockMvc.perform(get("/msg/0"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("Test message"));
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#adminAddMessage(com.engagement.model.dto.MessageDTO)}.
	 * @throws Exception 
	 */
	@Test
	void testAddMessageAdmin() throws Exception {
		Mockito.when(messageService.adminAddMessage(messageAdminDTO)).thenReturn(mockAdminMessage);
		this.mockMvc.perform(post("/msg/admin")
				.contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(messageAdminDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message").value("Hello from MessageAdminDTO"));
	}

	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#clientAddMessage(com.engagement.model.dto.MessageDTO)}.
	 * @throws Exception 
	 */
	@Test
	void testClientAddMessage() throws Exception {
		Mockito.when(messageService.clientAddMessage(messageClientDTO)).thenReturn(mockClientMessage);
		this.mockMvc.perform(post("/msg/client")
				.contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(messageClientDTO)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.message").value("Hello from MessageClientDTO"));
	}

	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#deleteMessage(int)}.
	 * @throws Exception 
	 */
	@Test
	void testDeleteMessage() throws Exception {
		Mockito.when(messageService.deleteMessage(0)).thenReturn("Deleted");
		this.mockMvc.perform(delete("/msg/{messageId}", 0)).andExpect(content().string("Deleted"));
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getClientByMessage(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	void testGetClientByMessage() throws Exception {
		Mockito.when(messageService.findByMessage("Test message")).thenReturn(testMessage);
		this.mockMvc.perform(get("/msg/clients/{message}", testMessage.getMessage()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("test title"));
	}

	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getClientMessageById(int)}.
	 * @throws Exception 
	 */
	@Test
	void testGetClientMessageById() throws Exception {
		List<Message> clientMessages = new ArrayList<>();
		clientMessages.add(mockClientMessage);
		Mockito.when(messageService.findByClientId(0)).thenReturn(clientMessages);
		this.mockMvc.perform(get("/msg/client/{clientId}", client0.getClientId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getAdminMessageById(int)}.
	 * @throws Exception 
	 */
	@Test
	void testGetAdminMessageById() throws Exception {
		List<Message> adminMessages = new ArrayList<>();
		adminMessages.add(mockAdminMessage);
		Mockito.when(messageService.findByAdminId(0)).thenReturn(adminMessages);
		this.mockMvc.perform(get("/msg/admin/{adminId}", admin0.getAdminId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getClientMessageByEmail(String)}.
	 * @throws Exception
	 */
	@Test
	void testGetClientMessageByEmail() throws Exception {
		List<Message> clientMessages = new ArrayList<>();
		clientMessages.add(mockClientMessage);
		Mockito.when(messageService.findByClientEmail("client0@a")).thenReturn(clientMessages);
		this.mockMvc.perform(get("/msg/clientemail/{clientEmail}", client0.getEmail()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));;
		
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getAdmineMessageByEmail(String)}.
	 * @throws Exception 
	 */
	@Test
	void testGetAdmineMessageByEmail() throws Exception {
		List<Message> adminMessages = new ArrayList<>();
		adminMessages.add(mockAdminMessage);
		Mockito.when(messageService.findByAdminEmail("admin0@b")).thenReturn(adminMessages);
		this.mockMvc.perform(get("/msg/adminemail/{adminEmail}", admin0.getEmail()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#getMessageByEmail(String)}.
	 * @throws Exception
	 */
	@Test
	void testGetMessageByEmail() throws Exception {
		List<Message> adminMessages = new ArrayList<>();
		List<Message> clientMessages = new ArrayList<>();
		adminMessages.add(mockAdminMessage);
		clientMessages.add(mockClientMessage);
		Mockito.when(messageService.findMessageByEmail("admin0@b")).thenReturn(clientMessages);
		this.mockMvc.perform(get("/message/{email}",admin0.getEmail()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));
		Mockito.when(messageService.findMessageByEmail("client0@a")).thenReturn(clientMessages);
		this.mockMvc.perform(get("/message/{email}",client0.getEmail()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)));
		
	}
 }
