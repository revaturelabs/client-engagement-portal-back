package com.engagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

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

}
