/**
 * 
 */
package com.engagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import com.engagement.service.MessageService;

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
	
	Client client0 = new Client(0, "client0@a.net", "walmart", "573-555-3535");
	Admin admin0= new Admin(0,"admin0@b","firstnameadmin0","lastnameadmin0");
	
	MessageAdminDTO messageAdminDTO = new MessageAdminDTO(client0.getClientId(), admin0.getAdminId(), "Hello from MessageAdminDTO");
	MessageClientDTO messageClientDTO = new MessageClientDTO(admin0.getAdminId(), client0.getClientId(), "Hello from MessageClientDTO");
	
	Message mockAdminMessage = new Message(0,true, admin0, client0, messageAdminDTO.getMessage(), null, false);
	Message mockClientMessage = new Message(0,false, admin0, client0, messageClientDTO.getMessage(), null, false);
	
	Message testMessage = new Message(0, true, admin0, client0, "Test message", null, false);
	
	List<Message> messages = new ArrayList<>();
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#MessageController(com.engagement.service.MessageService)}.
	 */
	
	/**
	 * Test method for {@link com.engagement.controller.MessageController#findAll()}.
	 * @throws Exception 
	 */
	@Test
	void testFindAll() throws Exception {
		messages.add(mockAdminMessage);
		messages.add(mockClientMessage);
		Mockito.when(messageService.getMessages()).thenReturn(messages);
		this.mockMvc.perform(get("/msg")).andExpect(status().isOk()).andReturn();
	}


}
