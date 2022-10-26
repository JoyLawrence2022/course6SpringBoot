package com.capstone.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter
@Setter
public class MyUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String userName;
	
	@Column
	private String userpwd;
	
	@OneToMany(mappedBy = "myuser", cascade = CascadeType.ALL)
	private Set<Roles> roles = new HashSet<>();
	
	//@OneToOne(mappedBy = "myuser1", cascade = CascadeType.ALL)
	//private Address address;
	
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
		
		for(Roles r : roles) {
			r.setMyuser(this);
		}
	}
}
