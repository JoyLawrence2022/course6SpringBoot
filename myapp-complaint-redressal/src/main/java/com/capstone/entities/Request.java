package com.capstone.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
	
	private String userName;
	private String userpwd;
	private List<String> roles;

}
