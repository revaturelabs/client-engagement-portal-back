package com.engagement.service;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;
import com.engagement.model.dto.MessageAdminDTO;
import com.engagement.model.dto.MessageClientDTO;
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
    return message;
  }

  public String deleteMessage(int messageId){
    Message message = messageRepo.findById(messageId);
    if (message == null){
      return "Message NOT found";
    }
    messageRepo.delete(message);
    return ("Message id: " + messageId + " has successfully deleted");
  }
  
  public Message findByMessage (String message) {
	  return messageRepo.findByMessage(message);
  }
  
  public List<Message> findByClientId(int clientId) {
	  Client client1 = clientRepo.findById(clientId);
	  return messageRepo.findByclientId(client1);
  }
  
  public List<Message> findByAdminId(int adminId) {
	  Admin admin1 = adminRepo.findByAdminId(adminId);
	  return messageRepo.findByadminId(admin1);
  }

	public List<Message> findByClientEmail(String clientEmail) {
		Client client2 = clientRepo.findByEmail(clientEmail);
		return messageRepo.findByclientId(client2);
	}
	
	public List<Message> findByAdminEmail(String adminEmail) {
		Admin admin2 = adminRepo.findByEmail(adminEmail);
		return messageRepo.findByadminId(admin2);
		
	}
	
	public List<Message> findMessageByEmail(String email) {
		Client client2 = clientRepo.findByEmail(email);
		if(client2 == null) {
			Admin admin2 = adminRepo.findByEmail(email);
			return messageRepo.findByadminId(admin2);
		}
		return messageRepo.findByclientId(client2);
	}

	public Message adminAddMessage(MessageAdminDTO messageAdminDTO) {
		Admin admin = adminRepo.findByEmail(messageAdminDTO.getAdminEmail());
		Client client = clientRepo.findByEmail(messageAdminDTO.getClientEmail());
		
		Message message = new Message(0,true,admin, client,messageAdminDTO.getMessage(),null,false,messageAdminDTO.getTitle());
		return messageRepo.save(message);
	}

	public Message clientAddMessage(MessageClientDTO messageClientDTO) {
		Admin admin = adminRepo.findByEmail(messageClientDTO.getAdminEmail());
		Client client = clientRepo.findByEmail(messageClientDTO.getClientEmail());
		Message message = new Message(0,false,admin, client,messageClientDTO.getMessage(),null,false,messageClientDTO.getTitle());
		return messageRepo.save(message);
	}

	

	

}
