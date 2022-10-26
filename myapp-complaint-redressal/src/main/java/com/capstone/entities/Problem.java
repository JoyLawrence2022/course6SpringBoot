package com.capstone.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "problem")
@Entity
@Getter
@Setter
public class Problem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String problem_name;
	
	@Column
	private String complaintTrackingNumber;
	
	@Column
	private String ticket_status;
	
	@Column
	private boolean active_status;
	
	@Column
	private String engrName;
	
	@Column
	private String customerName;
	
	@Column
	private String tel_no;
	
	@Column
	private int pincode;
	
//	private List<Problem> problems (String engr_name) {
//		return List<Problem> pr
//	}
}
