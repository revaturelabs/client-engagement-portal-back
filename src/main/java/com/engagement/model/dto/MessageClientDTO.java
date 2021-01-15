package com.engagement.model.dto;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageClientDTO {
  private int adminId;
  private int clientId;
  private String message;
}
