package com.capstone.repositories;

import java.util.List;

import com.capstone.entities.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemsRepo extends JpaRepository<Problem, Integer>{
	
	List<Problem> findByComplaintTrackingNumber(String complaintTrackingNumber);

}
