package com.engagement.service;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageAdminDTO;
import com.engagement.model.dto.MessageClientDTO;
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

  public Message getMessageById(int messageId){
    Message message = messageRepo.findById(messageId);
    if (message == null){
      return null;
    }
    return messageRepo.findById(messageId);
  }

  public Message addMessageAdmin(MessageAdminDTO messageAdminDTO){
	  Admin admin = adminRepo.findByAdminId(messageAdminDTO.getAdminId());
	    Client client = clientRepo.findById(messageAdminDTO.getClientId());
	    Message message = new Message(0,true,admin, client,messageAdminDTO.getMessage(),null,false,"You get a new message!");
	    return (Message) messageRepo.save(message);
  }
  public Message addMessageClient(MessageClientDTO messageClientDTO){
	  Admin admin = adminRepo.findByAdminId(messageClientDTO.getAdminId());
	    Client client = clientRepo.findById(messageClientDTO.getClientId());
	    Message message = new Message(0,false, admin, client,messageClientDTO.getMessage(),null,false,"You get a new message!");
	    return (Message) messageRepo.save(message);
  }

  public String deleteMessage(int messageId){
    Message message = messageRepo.findById(messageId);
    if (message == null){
      return "Message NOT found";
    }
    messageRepo.delete(message);
    return ("Message id: " + messageId + " has successfully deleted");
  }

}
