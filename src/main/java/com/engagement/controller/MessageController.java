package com.engagement.controller;

import com.engagement.exception.IllegalEmailException;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageAdminDTO;
import com.engagement.model.dto.MessageClientDTO;
import com.engagement.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MessageController {

  private MessageService messageService;

  @Autowired
  public MessageController (MessageService messageService){
    super();
    this.messageService = messageService;
  }

  @GetMapping("/msg")
  public List<Message> findAll(){
    return messageService.getMessages();
  }

  @GetMapping("/msg/{messageId}")
  public Message getMessageById(@PathVariable int messageId){
    return messageService.getMessageById(messageId);
  }

  @DeleteMapping("/msg/{messageId}")
  public String deleteMessage(@PathVariable int messageId){
    return messageService.deleteMessage(messageId);
  }

  @GetMapping("/msg/clients/{message}")
  public Message getClientByMessage (@PathVariable String message) {
	  return messageService.findByMessage(message);
  }
  
  @GetMapping("/msg/client/{clientId}")
  public List<Message> getClientMessageById (@PathVariable int clientId) {
	  return messageService.findByClientId(clientId);
  }
  
  @GetMapping("/msg/admin/{adminId}")
  public List<Message> getAdminMessageById (@PathVariable int adminId) {
	  return messageService.findByAdminId(adminId);
  }
  
  @GetMapping("/msg/clientemail/{clientEmail}")
  public List<Message>  getClientMessageByEmail (@PathVariable String clientEmail) {
	  return messageService.findByClientEmail(clientEmail);
  }
  
  @GetMapping("/msg/adminemail/{adminEmail}")
  public List<Message> getAdminMessageByEmail (@PathVariable String adminEmail) {
	  return messageService.findByAdminEmail(adminEmail);
  }
  
  @GetMapping("/message/{email}")
  public List<Message> getMessageByEmail (@PathVariable String email) throws IllegalEmailException {
	  return messageService.findMessageByEmail(email);
  }
  
  @PostMapping("/msg/admin")
  public Message adminAddMessage (@RequestBody MessageAdminDTO messageAdminDTO) {
	  return messageService.adminAddMessage(messageAdminDTO);
  }
  
  @PostMapping("/msg/client")
  public Message clientAddMessage (@RequestBody MessageClientDTO messageClientDTO) {
	  return messageService.clientAddMessage(messageClientDTO);
  }
}
