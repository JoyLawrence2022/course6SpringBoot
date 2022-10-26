package com.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestInitiateProblem {
	
	private int id;
	private String problem_name;
	private String complaintTrackingNumber;
	private String ticket_status;
	private boolean active_status;
	private String engrName;
	private String customerName;
	private String tel_no;
	
}
