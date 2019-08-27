package com.ravi.housing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ravi.housing.domain.Tenant;

@Repository
public interface RentRepository extends JpaRepository<Tenant, Long> {

	Tenant findByHouseHouseId(Long houseId);

	void deleteByHouseHouseId(Long houseId);

	void deleteByTenantId(Long tenantId);

}
