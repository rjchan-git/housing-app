package com.ravi.housing.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {

	@Id
	@GeneratedValue
	private Long addressId;
	
	@NotEmpty(message = "Street name needs to be provided")
	private String street;
    
	@NotEmpty(message = "House number needs to be provided")
	@Column(name = "HOUSE_NUMBER")
	private String houseNumber;
    
	@NotEmpty(message = "Zip code needs to be provided")
	private String zipCode;
    
	@NotEmpty(message = "city needs to be provided")
	private String city;
    
	@NotEmpty(message = "state needs to be provided")
	private String state;
    
	@OneToOne(mappedBy = "address")
	@Valid
    private House house;

    public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	@JsonIgnore
	public House getHouse() {
		return house;
	}
	
	public void setHouse(House house) {
		this.house = house;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
    @Override
	public String toString() {
		return "Address [addressId=" + addressId + ", house=" + house + ", street=" + street + ", houseNumber="
				+ houseNumber + ", zipCode=" + zipCode + ", city=" + city + ", state=" + state + "]";
	}
}
