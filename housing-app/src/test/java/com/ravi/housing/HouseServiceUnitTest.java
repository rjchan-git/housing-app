package com.ravi.housing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.ravi.housing.domain.Address;
import com.ravi.housing.domain.Contract;
import com.ravi.housing.domain.House;
import com.ravi.housing.domain.Status;
import com.ravi.housing.domain.Tenant;
import com.ravi.housing.domain.User;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.repo.ContractRepository;
import com.ravi.housing.repo.HousingRepository;
import com.ravi.housing.repo.RentRepository;
import com.ravi.housing.repo.UserRepository;
import com.ravi.housing.service.HousingService;

@RunWith(MockitoJUnitRunner.class)
public class HouseServiceUnitTest {
	

	@InjectMocks
	private HousingService housingService;
	
	@Mock
	private HousingRepository housingRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ContractRepository contractRepository;
	
	@Mock
	private RentRepository rentRepository;

	private House house;
	
	private List<House> houses;
	
	private User user;

	private List<User> users;

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		house = new House();
		houses = new ArrayList<House>();
		house.setBalcony(true);
		house.setDescription("House is beautiful");
		house.setNoOfBedRooms(3);
		house.setNoOfRooms(2);
		house.setPetsAllowed(true);
		house.setBalcony(false);
		house.setRent(2000.00);
		Address address = new Address();
		address.setCity("Munich");
		address.setHouseNumber("12");
		address.setStreet("Grunwalder Str");
		house.setAddress(address);
		houses.add(house);

		user = new User();
		users = new ArrayList<User>();
		user.setFirstName("Ravi");
		user.setLastName("Jagarlapudi");
		user.setEmailAddress("ravi@gmail.com");
		user.setMobileNumber("9632569878");
		user.setId(1000L);
		house.setUser(user);
		users.add(user);
	}
	
	@Test
	public void testAddHouse() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.of(user));
		housingService.createHouse(1000L, house);
		verify(housingRepository, times(1)).save(house);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testaddHouseWithNoUser() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.empty());
		housingService.createHouse(1000L, house);
	}
	
	@Test
	public void testGetAllHouses() throws UserNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(houses);
		List<House> housesTest = housingService.getHouseDetailsForUser(1000L, null);
		assertEquals(housesTest.size(), 1);
	}
	
	@Test
	public void testGetAllHousesWithStatusAvailable() throws UserNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(houses);
		List<House> housesTest = housingService.getHouseDetailsForUser(1000L, Status.AVAILABLE.toString());
		assertEquals(housesTest.size(), 1);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testGetAllHousesWithNoUser() throws UserNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(null);
		housingService.getHouseDetailsForUser(1000L, null);
	}
	
	@Test
	public void testGetHouseById() throws HouseNotFoundException {
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		House houseTest = housingService.getHouseDetails(2000L);
		assertEquals(houseTest, house);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void testGetHouseByIdWithNoHouse() throws HouseNotFoundException {
		when(housingRepository.findById(2000L)).thenReturn(Optional.empty());
		housingService.getHouseDetails(2000L);
	}
	
	@Test
	public void testDeleteHouse() throws HouseNotFoundException {
		Tenant tenant = new Tenant();
		Contract contract = new Contract();
		contract.setDepositAmount(200.00);
		contract.setContractId(2000);
		tenant.setContract(contract);
		tenant.setEmail("email");
		tenant.setTenantId(1000L);
		house.setTenant(tenant);
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		housingService.deleteHouse(1000L, 2000L);
		verify(contractRepository, times(1)).deleteById(2000);
		verify(rentRepository, times(1)).deleteById(1000L);
		verify(housingRepository, times(1)).deleteById(2000L);
	}
	
	@Test
	public void testUpdateHouse() throws UserNotFoundException, HouseNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(houses);
		house.setHosueId(2000L);
		housingService.updateHouse(1000L, house, 2000L);
		verify(housingRepository, times(1)).save(house);
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testUpdateHouseWithNoUser() throws UserNotFoundException, HouseNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(null);
		housingService.updateHouse(1000L, house, 2000L);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void testUpdateHouseWithNoHouse() throws UserNotFoundException, HouseNotFoundException {
		when(housingRepository.findByUserId(1000L)).thenReturn(houses);
		housingService.updateHouse(1000L, house, 2000L);
	}
	
	
	@Test
	public void testCompareHouse() throws HouseNotFoundException, UserNotFoundException {
		String message = housingService.compareHouses(1000L, 2000L, 2000L);
		assertEquals(message,"both the houses are identical");
	}
}
