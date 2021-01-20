package com.engagement.model.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAdminDTO {
  private String clientEmail;
  private String adminEmail;
  private String title;
  private String message;
}
