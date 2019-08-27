package com.ravi.housing.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.housing.domain.User;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void addUser(User userDetails) throws ApplicationException {
		userDetails.setCreationDate(LocalDate.now());
		userDetails.setModifiedDate(LocalDate.now());
		if(userRepository.findByEmailAddress(userDetails.getEmailAddress())==null) {
			userRepository.save(userDetails);
		}else {
			throw new ApplicationException("Email has already been registered!");
		}
		
	}

	public List<User> getAllusers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}

	public User getUserDetails(Long userId) throws UserNotFoundException {
		Optional<User> user=  userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User with user id : " + userId + " does not exist");
		}
		return user.get();
	}
	
	@Transactional
	public void deleteUserDetails(Long userId) throws UserNotFoundException {
		getUserDetails(userId);
		userRepository.deleteById(userId);
		userRepository.flush();
	}

	public void updateUser(User user,Long userId) throws UserNotFoundException {
		User userFromDB = getUserDetails(userId);
		setUserDetails(user, userFromDB);
		userRepository.save(userFromDB);
	}

	private void setUserDetails(User user, User userFromDB) {
		userFromDB.setEmailAddress(user.getEmailAddress());
		userFromDB.setFirstName(user.getFirstName());
		userFromDB.setLastName(user.getLastName());
		userFromDB.setMobileNumber(user.getMobileNumber());
		userFromDB.setModifiedDate(LocalDate.now());
	}
}
