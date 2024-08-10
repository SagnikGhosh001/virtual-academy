package com.smsv2.smsv2.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {

	private String name;

	private int subId;
 
	private int teacherId;
	
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd",shape = Shape.STRING)
	private String deadline;
}
