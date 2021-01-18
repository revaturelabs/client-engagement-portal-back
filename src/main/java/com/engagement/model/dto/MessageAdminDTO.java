package com.engagement.model.dto;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAdminDTO {
  private int clientId;
  private int adminId;
  private String title;
  private String message;
}
