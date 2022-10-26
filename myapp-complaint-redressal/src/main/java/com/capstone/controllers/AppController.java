package com.capstone.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.capstone.entities.ComplaintDTO;
import com.capstone.entities.ComplaintResponse;
import com.capstone.entities.MyUser;
import com.capstone.entities.Problem;
import com.capstone.entities.Request;
import com.capstone.entities.RequestInitiateProblem;
import com.capstone.entities.Request_Customer;
import com.capstone.entities.Response;
import com.capstone.exceptions.InvalidUserCredentialsException;
import com.capstone.services.CustomerService;
import com.capstone.services.EngineerService;
import com.capstone.services.ManagerService;
import com.capstone.services.UserAuthService;
import com.capstone.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EngineerService engineerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/signin")
	public ResponseEntity<Response> signin(@RequestBody Request request) {
		
		Authentication authentication = null;
		try {
			//creating new authentication object
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserpwd()));
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		
		//System.out.println("In signin method");
		
		//Spring security User
		User user = (User)authentication.getPrincipal();
		Set<String> roles = user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());
		
		Response response = new Response();
		response.setRoles(roles.stream().collect(Collectors.toList()));
		
			//additional content added after watching code from javainuse.com
			//final UserDetails userDetails = userAuthService.loadUserByUsername(request.getUserName());
		
			final String token = jwtUtil.generateToken(authentication);
			response.setToken(token);
		
		//these lines above are additional content from javainuse.com to send token. removing this till the time token adding is not implemented.
		
		
		return new ResponseEntity<Response>(response, HttpStatus.OK); //temporarily removing this to prevent roles going to front end
		//return new ResponseEntity<String>("User successfully signed in", HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Request request) {
		userAuthService.saveUser(request);
		//can check this response in Postman
		return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
	}
	
	/*
	@DeleteMapping("/admin/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestBody Request request) {
		userAuthService.deleteUser(request);
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}
	*/
	
	@DeleteMapping("/admin/deleteUser/{userName}")
	public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName) {
		System.out.println("Received User name is : " + userName);
		userAuthService.deleteUser(userName);
		return new ResponseEntity<String>("User deleted successfully", HttpStatus.OK);
	}
	
	@GetMapping("/admin/viewAllUsers")
	public ResponseEntity<List<String>> viewAllUsers(){
		return new ResponseEntity<List<String>> (userAuthService.viewAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody Request_Customer request_Customer) {
		userAuthService.saveCustomer(request_Customer);
		return new ResponseEntity<String>("Customer details saved successfully", HttpStatus.OK);
	}
	
	@PostMapping("/customer/raiseComplaint")
	public ResponseEntity<String> placeComplaint(@RequestBody RequestInitiateProblem requestInitiateProblem) {
		customerService.placeComplaint(requestInitiateProblem);
		return new ResponseEntity<String>("Complaint added successfully", HttpStatus.OK);
	}

	@PostMapping("/customer/viewComplaints/{userName}")
	public ResponseEntity<List<Problem>> viewProblemsRaisedByCustomer(@PathVariable("userName") String userName) {
	//	customerService.viewComplaintsByCustomer(userName);
		return new ResponseEntity<List<Problem>> (customerService.viewComplaintsByCustomer(userName), HttpStatus.OK);
	}
	
	@PostMapping("/manager/viewComplaints")
	public ResponseEntity<List<Problem>> managerViewAllComplaints() {
		return new ResponseEntity<List<Problem>>(managerService.getAllProblems(), HttpStatus.OK);
	}
	
	@PostMapping("/manager/viewComplaint/{tracker_no}")
	public ResponseEntity<List<Problem>> managerViewComplaint(@PathVariable("tracker_no") String tracker_no) {
		return new ResponseEntity<List<Problem>>(managerService.getComplaint(tracker_no), HttpStatus.OK);
	}
	
	@PostMapping("/manager/assignEngineer")
	public ResponseEntity<String> assignEngineerToComplaint(@RequestBody RequestInitiateProblem requestInitiateProblem) {
		managerService.assignEngineer(requestInitiateProblem);
		return new ResponseEntity<String>("Engineer assigned successfully", HttpStatus.OK); 
	}
	
	@PostMapping("/engineer/viewAssignedProblems/{engrName}")
	public ResponseEntity<List<Problem>> viewProblemsByEngineer(@PathVariable("engrName") String engrName) {
		return new ResponseEntity<List<Problem>> (engineerService.viewAssignedProblems(engrName), HttpStatus.OK);
	}

	@PostMapping("/engineer/updateProblem")
	public ResponseEntity<String> updateComplaintByEngineer(@RequestBody RequestInitiateProblem requestInitiateProblem) {
		engineerService.updateProblem(requestInitiateProblem);
		return new ResponseEntity<String> ("Complaint updated successfully", HttpStatus.OK);
	}
	
	
}

/*
@PostMapping("/customer/viewComplaints")
public ResponseEntity<List<Problem>> viewProblemsRaisedByCustomer(@RequestBody RequestInitiateProblem requestInitiateProblem) {
	return new ResponseEntity<List<Problem>> (customerService.viewComplaintsByCustomer(requestInitiateProblem), HttpStatus.OK);
}
*/

