package com.capstone.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "roles")
@Entity
@Getter
@Setter
public class Roles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String roles;
	
	@JoinColumn(name = "user_id") //column will be created with name "user_id"
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private MyUser myuser;
}
