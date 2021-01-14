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
  private Client clientId;
  private Admin adminId;
  private String message;


}
