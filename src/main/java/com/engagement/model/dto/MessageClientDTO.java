package com.engagement.model.dto;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageClientDTO {
  private int adminId;
  private int clientId;
  private String title;
  private String message;

}
