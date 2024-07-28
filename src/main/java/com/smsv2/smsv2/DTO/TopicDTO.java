package com.smsv2.smsv2.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
	private String name;
	
	private String link;
	
	
	private int subId;
	
	private int teacherId;
}
