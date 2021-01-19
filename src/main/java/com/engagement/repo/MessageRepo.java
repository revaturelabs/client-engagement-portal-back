package com.engagement.repo;


import com.engagement.model.Admin;
import com.engagement.model.Client;
import com.engagement.model.Message;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MessageRepo extends JpaRepository <Message, Integer>{
	
	

  public List<Message> findAll(); //Find all of the messages

  public Message findById(int messageId); //Find message by id

  public Message deleteById(int messageId);

//  this part is new, need some tests
  public Message findByMessage (String message);

  public List<Message> findByclientId(Client client);
  
  public List<Message> findByadminId(Admin admin);
  
}
