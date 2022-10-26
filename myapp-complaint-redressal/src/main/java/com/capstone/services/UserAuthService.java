package com.capstone.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.capstone.entities.Customer;
import com.capstone.entities.MyUser;
import com.capstone.entities.Problem;
import com.capstone.entities.Request;
import com.capstone.entities.Request_Customer;
import com.capstone.entities.Roles;
import com.capstone.repositories.CustomerRepo;
import com.capstone.repositories.ProblemRepo;
import com.capstone.repositories.UserRepo;
import com.capstone.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ProblemRepo problemRepo;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = userRepository.findByUserName(username).get();
		
		//Roles list from our Roles class
		List<Roles> userRoles = user.getRoles().stream().collect(Collectors.toList());
		
		//Granted authorities provided by Spring Security
		//Spring Security converts User Roles to Granted Authorities
		List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(r -> {
			return new SimpleGrantedAuthority(r.getRoles());
		}).collect(Collectors.toList());
		
		return new User(username, user.getUserpwd(), grantedAuthorities);
	}
	
	public void saveUser(Request request) {
		if(userRepository.findByUserName(request.getUserName()).isPresent()) {
			throw new RuntimeException("User already exists");
		}
		
		MyUser user = new MyUser();
		user.setUserName(request.getUserName());
		user.setUserpwd(passwordEncoder.encode(request.getUserpwd()));
		
		user.setRoles(request.getRoles().stream().map(r -> {
			Roles ur = new Roles();
			ur.setRoles(r);
			return ur;
		}).collect(Collectors.toSet()));
		
		userRepository.save(user);
	}
	
	/*
	public void deleteUser(Request request) {
		MyUser user = userRepository.findByUserName(request.getUserName()).get();
		userRepository.delete(user);
	}
	*/
	
	public void deleteUser(String userName) {
		MyUser user = userRepository.findByUserName(userName).get();
		userRepository.delete(user);
	}
	
	public List<String> viewAllUsers() {
		List<MyUser> users = userRepository.findAll();
		List<String> myList = new ArrayList<String>();
		for(int i=0; i<users.size(); i++) {
			String s = users.get(i).getUserName();
			myList.add(s);
		}
		return myList;
	}
	
	public void saveCustomer(Request_Customer req_Customer) {		
		Customer customer = new Customer();
		customer.setUserName(req_Customer.getUserName());
		customer.setPlot_no(req_Customer.getPlot_no());
		customer.setLocality(req_Customer.getLocality());
		customer.setDistrict(req_Customer.getDistrict());
		customer.setState(req_Customer.getState());
		customer.setPincode(req_Customer.getPincode());
		customer.setTel_no(req_Customer.getTel_no());
		customerRepo.save(customer);
	}

	/*
	public void saveProblemInitiatedByCustomer(Request_Problem req_Problem) {
		Problem problem = new Problem();
		problem.setProblem_name(req_Problem.getProblem_name());
		problem.setUser_id(req_Problem.getUser_id());
		//problem.setComplaint_no(req_Problem.getComplaint_no());
		problem.setTicket_status("Raised");
		problem.setActive_status(true);
		problemRepo.save(problem);		
	}
	*/

}
