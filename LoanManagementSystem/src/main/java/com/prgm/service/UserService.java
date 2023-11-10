package com.prgm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.prgm.entity.User;
import com.prgm.repository.UserRepository;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepo;
	
	public User autenticate(User user,Boolean chekifAdmin) throws Exception {
		
		Optional<User> dbusr = userRepo.findById(user.getUsername());
		if( dbusr.isPresent() && !user.getPassword().equals(dbusr.get().getPassword())) {
			System.out.println(dbusr.get().getPassword()+" | "+user.getPassword());
			throw new Exception("Credentials invalid");
		}
		if(chekifAdmin && !dbusr.get().getIsAdmin()) {
			throw new Exception("Not an Admin");
		}
		return dbusr.get();
		
	}

	public User register(User user) throws Exception {
		Optional<User> dbusr = userRepo.findById(user.getUsername());
		if( dbusr.isPresent() ) {
			throw new Exception("User already exists");
		}
		return userRepo.save(user);
	}

}
