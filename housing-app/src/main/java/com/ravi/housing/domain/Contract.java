package com.ravi.housing.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Contract {

	@Id
	@GeneratedValue
	private Integer contractId;

	@NotNull(message = "Please provide deposit amount")
	private Double depositAmount;


	@NotNull(message = "Please provide contract signed date")
	private LocalDate contractSignedDate;

	@NotNull(message = "Please provide start date of the contract")
	private LocalDate startDate;

	@NotNull(message = "Please provide end dateof the contract")
	private LocalDate endDate;

	@NotNull(message = "Please provide paid date")
	private LocalDate paidDate;
	

	@OneToOne
	@JoinColumn(name="TENANT_ID")
	private Tenant tenant;

	@NotNull(message = "please provide contarct status")
	private ContractStatus contractStatus= ContractStatus.ACTIVE;
	
	public ContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public LocalDate getContractSignedDate() {
		return contractSignedDate;
	}

	public void setContractSignedDate(LocalDate contractSignedDate) {
		this.contractSignedDate = contractSignedDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	@JsonIgnore
	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public LocalDate getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}

	@Override
	public String toString() {
		return "Contract [contract_id=" + contractId + ", depositAmount="
				+ depositAmount + ", contractDate=" + contractSignedDate + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", paidDate=" + paidDate + "]";
	}

}