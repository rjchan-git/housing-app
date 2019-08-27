package com.ravi.housing.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravi.housing.domain.Contract;
import com.ravi.housing.domain.House;
import com.ravi.housing.domain.Status;
import com.ravi.housing.domain.Tenant;
import com.ravi.housing.domain.TenantStatus;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.repo.ContractRepository;
import com.ravi.housing.repo.HousingRepository;
import com.ravi.housing.repo.RentRepository;

@Service
public class RentService {

	@Autowired
	private HousingRepository housingRepo;

	@Autowired
	private RentRepository rentRepository;

	@Autowired
	private ContractRepository contractRepository;

	public void rentHouse(Long houseId, Tenant client) throws HouseNotFoundException, ApplicationException {
		Optional<House> house = housingRepo.findById(houseId);
		if (!house.isPresent()) {
			throw new HouseNotFoundException("House with house id : " + houseId + " does not exist");
		}
		if (house.get().getTenant() != null && house.get().getHosueId().equals(houseId)) {
			throw new ApplicationException("House " + houseId + " has a tenant already");
		}
		House houseFromDb = house.get();
		houseFromDb.setStatus(Status.RENTED);
		houseFromDb.setTenant(client);
		Contract contract = client.getContract();
		contract.setContractSignedDate(LocalDate.now());
		client.setContract(contract);
		contract.setTenant(client);
		rentRepository.save(client);
	}

	public Tenant getTenantDetails(Long houseId) throws HouseNotFoundException {
		Tenant tenant = rentRepository.findByHouseHouseId(houseId);
		if (tenant == null) {
			throw new HouseNotFoundException("Tenant not found for the house id :" + houseId);
		}
		return tenant;
	}

	@Transactional
	public void deleteTenant(Long houseId, Long tenantId) throws HouseNotFoundException, ApplicationException {
		Tenant tenant = getTenantDetails(houseId);
		if (tenant != null) {
			if (!tenantId.equals(tenant.getTenantId())) {
				throw new ApplicationException("Tenant with tenant id " + tenantId + " does not exist");
			} else {
				House house = housingRepo.findByTenantTenantId(tenantId);
				house.setStatus(Status.AVAILABLE);
				house.setTenant(null);
				contractRepository.deleteById(tenant.getContract().getContractId());
				contractRepository.flush();
				rentRepository.deleteById(tenantId);
				rentRepository.flush();
			}
		}
	}

	public void updateTenantDetails(Long tenantId, Tenant tenant, Long houseId)
			throws HouseNotFoundException, ApplicationException {
		Optional<House> house = housingRepo.findById(houseId);
		if (!house.isPresent()) {
			throw new HouseNotFoundException("House with house id : " + houseId + " does not exist");
		}
		Optional<Tenant> tenantFromDb = rentRepository.findById(tenantId);
		if (!tenantFromDb.isPresent()) {
			throw new ApplicationException("tenant with tenant id : " + tenantId + " does not exist");
		}
		updateTenantDetails(tenantFromDb.get(), tenant, house.get());
		rentRepository.save(tenantFromDb.get());
	}

	private void updateTenantDetails(Tenant tenantFromDb, Tenant tenant, House house) {
		Contract contract = tenant.getContract();
		if (!TenantStatus.INACTIVE.equals(tenant.getTenantStatus())) {
			tenantFromDb.setAddress(tenant.getAddress());
			tenantFromDb.setClientDetails(tenant.getClientDetails());
			tenantFromDb.getContract().setContractSignedDate(contract.getContractSignedDate());
			tenantFromDb.getContract().setDepositAmount(contract.getDepositAmount());
			tenantFromDb.getContract().setEndDate(contract.getEndDate());
			tenantFromDb.getContract().setPaidDate(contract.getPaidDate());
			tenantFromDb.getContract().setStartDate(contract.getStartDate());
			tenantFromDb.setContract(tenant.getContract());
			tenantFromDb.setEmail(tenant.getEmail());
			tenantFromDb.setName(tenant.getName());
			tenantFromDb.setPhone(tenant.getPhone());
			tenantFromDb.getContract().setContractStatus(contract.getContractStatus());
		} else if (TenantStatus.INACTIVE.equals(tenant.getTenantStatus())) {
			house.setTenant(null);
			housingRepo.save(house);
			tenantFromDb.getContract().setEndDate(LocalDate.now());
			tenantFromDb.setTenantStatus(tenant.getTenantStatus());
			tenantFromDb.getContract().setContractStatus(contract.getContractStatus());
		}
	}

}
