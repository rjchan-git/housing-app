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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ravi.housing.domain.House;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.service.HousingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class HouseController {

	@Autowired
	private HousingService housingService;

	@RequestMapping(value = "houses/test", method = RequestMethod.GET)
	public String testPaymentController() {
		return "Hi! from Housing controller";
	}

	@ApiOperation(value = "Get all the houses for the user ", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all the houses for the user"),
			@ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later") })
	@RequestMapping(value = "users/{userId}/houses", method = RequestMethod.GET)
	public List<House> getHosuesOfUser(@PathVariable("userId") Long userId,
			@RequestParam(value = "status", required = false) String status) throws UserNotFoundException {
		return housingService.getHouseDetailsForUser(userId, status);
	}

	@ApiOperation(value = "Get the house details", response = House.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved house details"),
			@ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later") })
	@RequestMapping(value = "/houses/{id}", method = RequestMethod.GET)
	public House getHouseDetails(@PathVariable Long id) throws HouseNotFoundException {
		return housingService.getHouseDetails(id);
	}

	@ApiOperation(value = "Add house for a user", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added house for user"),
			@ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later") })
	@RequestMapping(value = "users/{userId}/houses", method = RequestMethod.POST)
	public ResponseEntity<String> addHouse(@Valid @RequestBody House houseDetails, @PathVariable Long userId)
			throws UserNotFoundException {
		housingService.createHouse(userId, houseDetails);
		return new ResponseEntity<String>("A new house has been added for the user : " + userId, HttpStatus.OK);
	}

	@ApiOperation(value = "Update the house details of the user", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated house details"),
			@ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later") })
	@RequestMapping(value = "users/{userId}/houses/{houseId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateHouse(@Valid @RequestBody House houseDetails, @PathVariable Long userId,
			@PathVariable("houseId") Long houseId) throws UserNotFoundException, HouseNotFoundException {
		housingService.updateHouse(userId, houseDetails, houseId);
		return new ResponseEntity<String>(
				"House with house id : " + houseId + " has been updated for the user : " + userId, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete the house details of user ", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted house details"),
			@ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later") })
	@RequestMapping(value = "users/{userId}/houses/{houseId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteHouse(@PathVariable Long userId, @RequestBody House houseDetails,
			@PathVariable("houseId") Long houseId) throws UserNotFoundException, HouseNotFoundException {
		housingService.deleteHouse(userId, houseId);
		return new ResponseEntity<String>(
				"House with house id : " + houseId + " has been deleted for the user : " + userId, HttpStatus.OK);
	}
}