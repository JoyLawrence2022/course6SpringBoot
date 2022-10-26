package com.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request_Customer {
	private String userName;
	private String plot_no;
	private String locality;
	private String district;
	private String state;
	private int pincode;
	private String tel_no; 
}
