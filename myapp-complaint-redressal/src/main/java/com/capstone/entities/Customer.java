package com.capstone.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "customer")
@Entity
@Getter
@Setter
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String userName;
	
	@Column
	private String plot_no;
	
	@Column
	private String locality;
	
	@Column
	private String district;
	
	@Column
	private String state;
	
	@Column
	private int pincode;
	
	@Column
	private String tel_no; 
}
