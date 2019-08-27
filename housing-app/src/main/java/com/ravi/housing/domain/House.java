package com.ravi.housing.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class House {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((balcony == null) ? 0 : balcony.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((floorSpace == null) ? 0 : floorSpace.hashCode());
		result = prime * result + ((houseId == null) ? 0 : houseId.hashCode());
		result = prime * result + ((noOfBedRooms == null) ? 0 : noOfBedRooms.hashCode());
		result = prime * result + ((noOfRooms == null) ? 0 : noOfRooms.hashCode());
		result = prime * result + ((petsAllowed == null) ? 0 : petsAllowed.hashCode());
		result = prime * result + ((rent == null) ? 0 : rent.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		House other = (House) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (balcony == null) {
			if (other.balcony != null)
				return false;
		} else if (!balcony.equals(other.balcony))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (floorSpace == null) {
			if (other.floorSpace != null)
				return false;
		} else if (!floorSpace.equals(other.floorSpace))
			return false;
		if (houseId == null) {
			if (other.houseId != null)
				return false;
		} else if (!houseId.equals(other.houseId))
			return false;
		if (noOfBedRooms == null) {
			if (other.noOfBedRooms != null)
				return false;
		} else if (!noOfBedRooms.equals(other.noOfBedRooms))
			return false;
		if (noOfRooms == null) {
			if (other.noOfRooms != null)
				return false;
		} else if (!noOfRooms.equals(other.noOfRooms))
			return false;
		if (petsAllowed == null) {
			if (other.petsAllowed != null)
				return false;
		} else if (!petsAllowed.equals(other.petsAllowed))
			return false;
		if (rent == null) {
			if (other.rent != null)
				return false;
		} else if (!rent.equals(other.rent))
			return false;
		if (status != other.status)
			return false;
		if (tenant == null) {
			if (other.tenant != null)
				return false;
		} else if (!tenant.equals(other.tenant))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Id
	@GeneratedValue
	@Column(name="HOUSE_ID")
	private Long houseId;

	@Column(name="NO_OF_ROOMS")
	@NotNull(message = "Please provide no of rooms")
	private Integer noOfRooms;
	
	@Column(name="DESCRIPTION")
	@NotNull(message = "Please provide description about the house")
	private String description;
	
	@Column(name="RENT")
	@NotNull(message = "Please provide the house rent")
	private Double rent;
	
	@Column(name="NO_OF_BEDROOMS")
	@NotNull(message = "Please provide the no of bed rooms")
	private Integer noOfBedRooms;
	
	@Column(name="FLOOR_SPACE")
	@NotNull(message = "Please provide floor space")
	private Long floorSpace;
	
	@Column(name="PETS_ALLOWED")
	@NotNull(message = "Please tell if pets are allowed")
	private Boolean petsAllowed;
	
	@Column(name="BALCONY")
	@NotNull(message = "Please tell if the house has balcony")
	private Boolean balcony;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="addressId")
	private Address address;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="tenantId")
	private Tenant tenant;
	
	private Status status = Status.AVAILABLE;

	public Long getHosueId() {
		return houseId;
	}

	public void setHosueId(Long hosueId) {
		this.houseId = hosueId;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public int getNoOfBedRooms() {
		return noOfBedRooms;
	}

	public void setNoOfBedRooms(int noOfBedRooms) {
		this.noOfBedRooms = noOfBedRooms;
	}

	public Long getFloorSpace() {
		return floorSpace;
	}

	public void setFloorSpace(Long floorSpace) {
		this.floorSpace = floorSpace;
	}

	public boolean isPetsAllowed() {
		return petsAllowed;
	}

	public void setPetsAllowed(boolean petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "House [id=" + houseId + ", noOfRooms=" + noOfRooms + ", user=" + user + "]";
	}

}
