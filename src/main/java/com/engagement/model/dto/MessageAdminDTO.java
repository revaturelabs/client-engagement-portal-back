package com.engagement.model.dto;

import com.engagement.model.Admin;
import com.engagement.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAdminDTO {
  private int clientId;
  private int adminId;
  private String message;


}
