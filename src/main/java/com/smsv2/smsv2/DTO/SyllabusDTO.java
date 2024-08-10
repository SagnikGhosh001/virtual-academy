package com.smsv2.smsv2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusDTO {
	private String name;
	
	private String link;
	
	
	
	private int dept;
	
	private int teacherId;
}
