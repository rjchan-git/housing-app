package com.ravi.housing.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="TENANT")
public class Tenant {

	@Id
	@GeneratedValue
	@Column(name ="TENANT_ID")
	private Long tenantId;

	@NotEmpty(message = "Tenant name needs to be provided")
	@Column(name ="NAME")
	private String name;

	@NotEmpty(message = "Tenant address needs to be provided")
	@Column(name ="ADDRESS")
	private String address;

	@NotNull(message = "Tenant phone needs to be provided")
	@Pattern(regexp = "[0-9]*" , message = "Phone number should contain only digits")
	@Column(name ="PHONE")
	private String phone;

	@NotEmpty(message = "Tenant email needs to be provided")
	@Email(message = "please provide a valid mail id")
	@Column(name ="EMAIL")
	private String email;
	
	public TenantStatus getTenantStatus() {
		return tenantStatus;
	}

	public void setTenantStatus(TenantStatus tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	@Column(name ="CLIENT_DETAILS")
	private String clientDetails;
	
	@NotNull(message = "Please provide tenant status")
	private TenantStatus tenantStatus= TenantStatus.ACTIVE;


	@OneToOne(mappedBy = "tenant" , cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private House house;
	
	@OneToOne(mappedBy = "tenant" , cascade = CascadeType.ALL , orphanRemoval = true)
	@Valid
	@NotNull(message = "Please provide contract detail")
    private Contract contract;
	
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(String clientDetails) {
		this.clientDetails = clientDetails;
	}
	@JsonIgnore
	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + tenantId + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", email=" + email + ", clientDetails=" + clientDetails + "]";
	}
}