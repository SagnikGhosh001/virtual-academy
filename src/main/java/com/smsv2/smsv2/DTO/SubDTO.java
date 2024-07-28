package com.smsv2.smsv2.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubDTO {
	private String subname;
	private int deptId;
	private int semId;
	private int teacherId;
	private int teacherChange;
}
