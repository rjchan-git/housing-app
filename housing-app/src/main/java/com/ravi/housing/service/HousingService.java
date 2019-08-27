package com.ravi.housing.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ravi.housing.domain.Address;
import com.ravi.housing.domain.House;
import com.ravi.housing.domain.TenantStatus;
import com.ravi.housing.domain.User;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.repo.ContractRepository;
import com.ravi.housing.repo.HousingRepository;
import com.ravi.housing.repo.RentRepository;
import com.ravi.housing.repo.UserRepository;

@Service
public class HousingService {

	@Autowired
	private HousingRepository housingRepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RentRepository rentRepository;

	@Autowired
	private ContractRepository contractRepository;

	public void createHouse(Long userId, House houseDetails) throws UserNotFoundException {
		Optional<User> userDetails = userRepository.findById(userId);
		if (!userDetails.isPresent()) {
			throw new UserNotFoundException("User with user id : " + userId + " does not exist");
		}
		houseDetails.setUser(userDetails.get());
		housingRepo.save(houseDetails);
	}

	public List<House> getHouseDetailsForUser(Long userId, String status)
			throws UserNotFoundException {
		List<House> houses = housingRepo.findByUserId(userId);
		if (CollectionUtils.isEmpty(houses)) {
			throw new UserNotFoundException("There are no houses prsent for  : " + userId + " does not exist");
		}
		if (!StringUtils.isEmpty(status)) {
			houses = houses.stream().filter(house -> house.getStatus().toString().equalsIgnoreCase(status))
					.collect(Collectors.toList());
		}
		return houses;
	}

	public House getHouseDetails(Long id) throws HouseNotFoundException {
		Optional<House> house = housingRepo.findById(id);
		if (!house.isPresent()) {
			throw new HouseNotFoundException("House with house id : " + id + " does not exist");
		}
		return house.get();
	}

	public void updateHouse(Long userId, House houseDetails, Long houseId)
			throws UserNotFoundException, HouseNotFoundException {
		List<House> houses = housingRepo.findByUserId(userId);
		if (CollectionUtils.isEmpty(houses)) {
			throw new UserNotFoundException("User with user id : " + userId + " does not exist");
		}
		Optional<House> houseFromDb = houses.stream().filter(house -> houseId.equals(house.getHosueId())).findFirst();
		if (!houseFromDb.isPresent()) {
			throw new HouseNotFoundException("House with house id : " + houseId + " does not exist");
		}
		setHouseDetails(houseFromDb.get(), houseDetails);
		housingRepo.save(houseFromDb.get());
	}

	private void setHouseDetails(House houseFromDb, House houseDetails) {
		houseFromDb.setNoOfRooms(houseDetails.getNoOfRooms());
		houseFromDb.setBalcony(houseDetails.isBalcony());
		houseFromDb.setPetsAllowed(houseDetails.isPetsAllowed());
		houseFromDb.setDescription(houseDetails.getDescription());
		houseFromDb.setFloorSpace(houseDetails.getFloorSpace());
		houseFromDb.setRent(houseDetails.getRent());
		houseFromDb.setNoOfBedRooms(houseDetails.getNoOfBedRooms());
		Address address = houseDetails.getAddress();
		houseFromDb.getAddress().setCity(address.getCity());
		houseFromDb.getAddress().setHouseNumber(address.getHouseNumber());
		houseFromDb.getAddress().setState(address.getState());
		houseFromDb.getAddress().setStreet(address.getStreet());
		houseFromDb.getAddress().setZipCode(address.getZipCode());
	}

	@Transactional
	public void deleteHouse(Long userId, Long houseId) throws HouseNotFoundException {
		House house = getHouseDetails(houseId);
		if (house.getTenant() != null) {
			contractRepository.deleteById(house.getTenant().getContract().getContractId());
			contractRepository.flush();
			rentRepository.deleteById(house.getTenant().getTenantId());
			rentRepository.flush();
		}
		housingRepo.deleteById(houseId);
		housingRepo.flush();
	}

	public String compareHouses(Long userId, Long house1, Long house2) throws HouseNotFoundException, UserNotFoundException {
		if(house1.equals(house2)) {
			return "both the houses are identical";
		}
		return "Houses are not identical";
	}
}