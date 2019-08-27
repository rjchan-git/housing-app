package com.ravi.housing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ravi.housing.domain.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer>{

	void deleteByTenantTenantId(Long tenantId);


}
