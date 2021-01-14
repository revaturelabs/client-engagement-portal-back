package com.engagement.controller;

import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageDTO;
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

//  @PostMapping("/msg")
//  public Message addMessage(@RequestBody MessageDTO messageDTO){
//    //String email =
//    return messageService.addMessage(messageDTO);
//  }

  @DeleteMapping("/msg/{messageId}")
  public String deleteMessage(@PathVariable int messageId){
    return messageService.deleteMessage(messageId);
  }



}
