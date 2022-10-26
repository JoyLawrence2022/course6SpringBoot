package com.capstone.services;

import java.util.List;

import com.capstone.entities.Problem;
import com.capstone.entities.RequestInitiateProblem;
import com.capstone.repositories.ProblemRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineerService {
	
	@Autowired
	ProblemRepo problemRepo;
	
	
	/*
	public Problem viewAssignedProblems(RequestInitiateProblem requestInitiateProblem) {
		Problem problem = problemRepo.findByEngrName(requestInitiateProblem.getEngr_name());
		return problem;
	}
	*/
	
	public List<Problem> viewAssignedProblems(String engineerName) {
		List<Problem> problems = problemRepo.findByEngrName(engineerName);
		return problems;
	}
	
	public void updateProblem(RequestInitiateProblem requestInitiateProblem) {
		Problem problem = problemRepo.findByComplaintTrackingNumber(requestInitiateProblem.getComplaintTrackingNumber());
		problem.setTicket_status(requestInitiateProblem.getTicket_status());
		problemRepo.save(problem);
	}
}
