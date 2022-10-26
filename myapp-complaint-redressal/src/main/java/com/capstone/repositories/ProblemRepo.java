package com.capstone.repositories;

import java.util.List;

import com.capstone.entities.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepo extends JpaRepository<Problem, Integer>{
	
	Problem findByComplaintTrackingNumber(String complaintTrackingNumber);
	//List<Problem> findByComplaintTrackingNumber(String complaintTrackingNumber);
	
	//Problem findByEngrName(String engrName);
	
	List<Problem> findByEngrName(String engrName);
	
	List<Problem> findByCustomerName(String customerName);
	
	

}
