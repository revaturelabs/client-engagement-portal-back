package com.engagement.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
	private String clientEmail;
	private String adminEmail;
	private String title;
	private String message;

}
