package com.ravi.housing.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ravi.housing.domain.User;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/test" , method = RequestMethod.GET)
	public String testPaymentController() {
		return "Hi! from user controller";
	}

	@ApiOperation(value = "Add User", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully added User"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value="user", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@Valid @RequestBody User user) throws ApplicationException {
		userService.addUser(user);
		return new ResponseEntity<String>("User has been successfully created ! " , HttpStatus.OK);
	}

	@ApiOperation(value = "Get all the user details", response= List.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Retrieved all the users"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})

	@RequestMapping(value="users", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.getAllusers();
	}

	@ApiOperation(value = "Get user for the user id :", response= User.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved user"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})

	@RequestMapping(value="users/{userId}", method = RequestMethod.GET)
	public User getUserDetails(@PathVariable Long userId) throws UserNotFoundException {
		return userService.getUserDetails(userId);
	}

	@ApiOperation(value = "Delete User", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully deleted user"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})

	@RequestMapping(value="users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteDetails(@PathVariable Long userId) throws UserNotFoundException {
		userService.deleteUserDetails(userId);
		return new ResponseEntity<String>("User details has been deleted for the user : " + userId , HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update User", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully updated user"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	
	@RequestMapping(value="users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@Valid @RequestBody User user, @PathVariable Long userId) throws UserNotFoundException {
		userService.updateUser(user,userId);
		return new ResponseEntity<String>("User details has been updated", HttpStatus.OK);
	}
	
}