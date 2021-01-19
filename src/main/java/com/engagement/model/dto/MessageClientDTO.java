package com.engagement.model.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageClientDTO {
  private String clientEmail;
  private String adminEmail;
  private String title;
  private String message;

}
