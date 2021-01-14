package com.engagement.service;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageDTO;
import com.engagement.repo.AdminRepo;
import com.engagement.repo.ClientRepo;
import com.engagement.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

  @Autowired
  private MessageRepo messageRepo;

  @Autowired
  private AdminRepo adminRepo;

  @Autowired
  private ClientRepo clientRepo;

  public List<Message> getMessages(){
    return messageRepo.findAll();
  }

//  public Message addMessage(MessageDTO dto){
//    //Admin admin = adminRepo.findByAdminId(dto.);
//    //Client client  = clientRepo.findByEmail(email);
//    Message message = new Message(dto.getMessage());
//    return (Message) messageRepo.save(message);
//  }

  public String deleteMessage(int messageId){
    Message message = messageRepo.findById(messageId);
    if (message == null){
      return "Message NOT found";
    }
    messageRepo.delete(message);
    return ("Message id: " + messageId + " has successfully deleted");
  }

}
