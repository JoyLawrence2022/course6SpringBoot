package com.capstone.repositories;

import com.capstone.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	Customer findByUserName(String userName);

}
