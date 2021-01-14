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

  @OneToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "admin_id")
  private Admin admin_id;

  @OneToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "client_id")
  private Client client_Id;

  private String message;

  @CreationTimestamp
  private LocalDateTime dateSent;

  private boolean readStatus;

//  @Enumerated(EnumType.STRING)
//  private haveBatch haveBatch;

}
