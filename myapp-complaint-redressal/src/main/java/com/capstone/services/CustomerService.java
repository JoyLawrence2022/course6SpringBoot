package com.capstone.services;

import java.util.List;
import java.util.UUID;

import com.capstone.entities.ComplaintDTO;
import com.capstone.entities.ComplaintResponse;
import com.capstone.entities.Customer;
import com.capstone.entities.MyUser;
import com.capstone.entities.Problem;
import com.capstone.entities.RequestInitiateProblem;
import com.capstone.repositories.CustomerRepo;
import com.capstone.repositories.ProblemRepo;
import com.capstone.repositories.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ProblemRepo problemRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	public ComplaintResponse placeComplaint(RequestInitiateProblem requestInitiateProblem) {
		
		String complaintTrackingNumber = generateUniqueTrackingNumber();
		//List<MyUser> u_name = userRepo.findByName(requestInitiateProblem.getCustomer_id());
		//u_name.toString();
		//userRepo.get
		//MyUser u = userRepo.findByName(requestInitiateProblem.getCustomer_id());
		Customer c = customerRepo.findByUserName(requestInitiateProblem.getCustomerName());
		
		Problem problem = new Problem();
		problem.setProblem_name(requestInitiateProblem.getProblem_name());
		problem.setCustomerName(requestInitiateProblem.getCustomerName());
		problem.setTel_no(c.getTel_no());
		problem.setPincode(c.getPincode());
		problem.setComplaintTrackingNumber(complaintTrackingNumber);
		problem.setTicket_status("Raised");
		problem.setActive_status(true);
		
		problemRepo.save(problem);
		
		return new ComplaintResponse(complaintTrackingNumber);
	}
	
	public List<Problem> viewComplaintsByCustomer(String name) {
		return problemRepo.findByCustomerName(name);
		//return problems;
	}
	
	
	
	/*
	 * 
	 public List<Problem> viewAssignedProblems(RequestInitiateProblem requestInitiateProblem) {
		List<Problem> problems = problemRepo.findByEngrName(requestInitiateProblem.getEngr_name());
		return problems;
	}
	
	public List<Problem> viewComplaintsByCustomer(RequestInitiateProblem requestInitiateProblem) {
		List<Problem> problems = problemRepo.findByCustomerName(requestInitiateProblem.getCustomer_id());
		return problems;
	}
	 */
	
	private String generateUniqueTrackingNumber() {
		return UUID.randomUUID().toString();
	}
}
