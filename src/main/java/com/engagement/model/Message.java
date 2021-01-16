package com.engagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

//  public Message(String message) {
//    super();
//    this.message = message;
//    //this.client_Id = client;
//  }

  public enum haveBatch{
    NOBATCH,
    BATCHID
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int messageId;

  private boolean isAdminTheSender;

  @OneToOne
  @JoinColumn(name = "admin_id")
  private Admin adminId;

  @OneToOne
  @JoinColumn(name = "client_id")
  private Client clientId;

  private String message;

  @CreationTimestamp
  private LocalDateTime dateSent;

  private boolean readStatus;
  private String title;

//  @Enumerated(EnumType.STRING)
//  private haveBatch haveBatch;
  public Message(boolean b, Admin admin_id, Client client_Id, String message, LocalDateTime dataSent, boolean readStatus, String title) {
	  this.isAdminTheSender = b;
	  this.adminId = admin_id;
	  this.clientId = client_Id;
	  this.message = message;
	  this.dateSent = dataSent;
	  this.readStatus = readStatus;
	  this.title=title;
	  
  }
}
