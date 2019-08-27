package com.ravi.housing.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ravi.housing.domain.House;

@Repository
public interface HousingRepository extends JpaRepository<House, Long>{

	List<House> findByUserId(Long id);

	House findByTenantTenantId(Long houseId);

}
