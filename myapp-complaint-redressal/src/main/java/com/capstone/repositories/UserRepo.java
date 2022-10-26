package com.capstone.repositories;

import java.util.List;
import java.util.Optional;
import com.capstone.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<MyUser, Integer> {
	Optional<MyUser> findByUserName(String userName);
	
	//MyUser findByUserName(String userName);
}
