package com.engagement.repo;

import com.engagement.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MessageRepo extends JpaRepository <Message, Integer>{

  public List<Message> findAll(); //Find all of the messages

  public Message findById(int messageId); //Find message by id

  public Message deleteById(int messageId);




}
