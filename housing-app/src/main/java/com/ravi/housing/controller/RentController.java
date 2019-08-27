package com.ravi.housing.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ravi.housing.domain.Tenant;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.service.RentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class RentController {

	@Autowired
	private RentService rentService;

	@ApiOperation(value = "Test for Rent controller", response= String.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Test for Rent Controller"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value = "rent/test", method = RequestMethod.GET)
	public String testRentController() {
		return "Hi! from Renting controller";
	}

	@ApiOperation(value = "Add tenant to the house", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully added tenant to the house"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value = "houses/{houseId}/tenant", method = RequestMethod.POST)
	public ResponseEntity<String> addTenant(@Valid @RequestBody Tenant tenant, @PathVariable Long houseId) throws HouseNotFoundException, ApplicationException {
		rentService.rentHouse(houseId, tenant);
		return new ResponseEntity<String>("A new tenant has been added for the house : " + houseId, HttpStatus.OK);
	}

	@ApiOperation(value = "Get the tenant details for the house", response= Tenant.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved tennat details"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value = "houses/{houseId}/tenant", method = RequestMethod.GET)
	public Tenant getTenantForHouse(@PathVariable Long houseId) throws HouseNotFoundException {
		return rentService.getTenantDetails(houseId);
	}

	@ApiOperation(value = "Delete the tenant for the house", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully deleted tenant"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value = "houses/{houseId}/tenant/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTenant(@PathVariable("houseId") Long houseId, @PathVariable("id") Long tenantId)
			throws HouseNotFoundException, ApplicationException {
		rentService.deleteTenant(houseId, tenantId);
		return new ResponseEntity<String>("Deleted the tenant for the house  " + houseId, HttpStatus.OK);
	}

	@ApiOperation(value = "Update the client details", response= ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully updated tenant details"),
	        @ApiResponse(code = 500, message = "There was an error proccesing the request. Please try again later")
	})
	@RequestMapping(value = "houses/{houseId}/tenant/{tenantId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTenant(@Valid @RequestBody Tenant tenant , @PathVariable("tenantId") Long tenantId,
			@PathVariable("houseId") Long houseId) throws HouseNotFoundException, ApplicationException {
		rentService.updateTenantDetails(tenantId, tenant, houseId);
		return new ResponseEntity<String>("Updated the tenant details for the house : " + houseId, HttpStatus.OK);
	}

}