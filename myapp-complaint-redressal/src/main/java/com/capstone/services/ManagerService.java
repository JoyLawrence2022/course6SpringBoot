package com.capstone.services;

import java.util.List;

import com.capstone.entities.Problem;
import com.capstone.entities.RequestInitiateProblem;
import com.capstone.repositories.ProblemRepo;
import com.capstone.repositories.ProblemsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
	
	@Autowired
	ProblemRepo problemRepo;
	
	@Autowired
	ProblemsRepo problemsRepo;
	
	public List<Problem> getAllProblems() {
		return problemRepo.findAll();
	}
	
	public void assignEngineer(RequestInitiateProblem requestInitiateProblem) {
		
		Problem problem = problemRepo.findByComplaintTrackingNumber(requestInitiateProblem.getComplaintTrackingNumber());
		problem.setEngrName(requestInitiateProblem.getEngrName());
		problem.setTicket_status("Assigned");
		problemRepo.save(problem);	
	}
	
	public List<Problem> getComplaint(String tracker_no) {
		return problemsRepo.findByComplaintTrackingNumber(tracker_no);
	}
	
}
