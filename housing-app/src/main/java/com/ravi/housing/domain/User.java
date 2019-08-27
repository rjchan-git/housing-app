package com.ravi.housing.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;
	
	@NotEmpty(message = "Firstname cannot be empty")
	@Size(min = 0, max = 15 , message = "First name can not be more than 15 characters")
	private String firstName;
	
	@NotEmpty(message = "Lastname cannot be empty")
	@Size(min = 0, max = 15 , message = "Last name can not be more than 15 characters")
	private String lastName;
	
	@Email(message = "Please provide a valid email address")
	private String emailAddress;
	
	@NotNull(message="Mobile number needs to be provided")
	@Pattern(regexp = "[0-9]*" , message = "Mobile number should contain only digits")
	private String mobileNumber;

	private LocalDate creationDate;
	
	private LocalDate modifiedDate;
	
	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHouse(List<House> house) {
		this.house = house;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<House> house;
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<House> getHouse() {
		return house;
	}

	public void setHouseDetails(List<House> house) {
		this.house = house;
	}
}