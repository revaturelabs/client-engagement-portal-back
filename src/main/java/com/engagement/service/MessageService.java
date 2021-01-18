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

  public Message addMessageAdmin(MessageAdminDTO messageAdminDTO){
	  Admin admin = adminRepo.findByAdminId(messageAdminDTO.getAdminId());
	    Client client = clientRepo.findById(messageAdminDTO.getClientId());
	    Message message = new Message(0,true,admin, client,messageAdminDTO.getMessage(),null,false,messageAdminDTO.getTitle());
	    return (Message) messageRepo.save(message);
  }
  public Message addMessageClient(MessageClientDTO messageClientDTO){
	  Admin admin = adminRepo.findByAdminId(messageClientDTO.getAdminId());
	    Client client = clientRepo.findById(messageClientDTO.getClientId());
	    Message message = new Message(0,false, admin, client,messageClientDTO.getMessage(),null,false,messageClientDTO.getTitle());
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
  
  public Message findByMessage (String message) {
	  return messageRepo.findByMessage(message);
  }
  
//this part is new, need some tests
  public List<Message> findByClientId(int clientId) {
	  Client client1 = clientRepo.findById(clientId);
//	  Client client2 = new Client(1,"client2@Walmart","Walmart","123-456-7890");
	  return messageRepo.findByclientId(client1);
  }
  
  public List<Message> findByAdminId(int adminId) {
	  Admin admin1 = adminRepo.findByAdminId(adminId);
	  return messageRepo.findByadminId(admin1);
  }

	public List<Message> findByClientEmail(String clientEmail) {
		Client client2 = clientRepo.findByEmail(clientEmail);
		System.out.println("ClientInfo: "+ client2);
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
			if (admin2 == null) {
				return null;
			}
			return messageRepo.findByadminId(admin2);
		}
		return messageRepo.findByclientId(client2);
	}

}
