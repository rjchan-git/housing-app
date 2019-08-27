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
import com.ravi.housing.domain.Tenant;
import com.ravi.housing.domain.TenantStatus;
import com.ravi.housing.domain.User;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.HouseNotFoundException;
import com.ravi.housing.repo.ContractRepository;
import com.ravi.housing.repo.HousingRepository;
import com.ravi.housing.repo.RentRepository;
import com.ravi.housing.repo.UserRepository;
import com.ravi.housing.service.RentService;

@RunWith(MockitoJUnitRunner.class)
public class RentServiceUnitTest {

	@InjectMocks
	private RentService rentService;

	@Mock
	private RentRepository rentRepository;

	@Mock
	private HousingRepository housingRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ContractRepository contractRepository;
	
	private House house;

	private List<House> houses;

	private User user;
	
	private List<User> users;
	
	private Tenant tenant;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		house = new House();
		houses = new ArrayList<House>();
		house.setBalcony(true);
		house.setDescription("House is beautiful");
		house.setNoOfBedRooms(3);
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
		
		tenant = new Tenant();
		Contract contract = new Contract();
		contract.setDepositAmount(200.00);
		contract.setContractId(5000);
		tenant.setContract(contract);
	}
	
	@Test
	public void addTenant() throws HouseNotFoundException, ApplicationException {
		when(housingRepository.findById(1000L)).thenReturn(Optional.of(house));
		rentService.rentHouse(1000L, tenant);
		verify(rentRepository,times(1)).save(tenant);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void addTenantWithNoHouse() throws HouseNotFoundException, ApplicationException {
		when(housingRepository.findById(1000L)).thenReturn(Optional.empty());
		rentService.rentHouse(1000L, tenant);
	}
	
	@Test(expected = ApplicationException.class)
	public void addTenantWithToSameHouse() throws HouseNotFoundException, ApplicationException {
		house.setHosueId(1000L);
		house.setTenant(tenant);
		when(housingRepository.findById(1000L)).thenReturn(Optional.of(house));
		rentService.rentHouse(1000L, tenant);
	}
	
	@Test
	public void testGetTenantDetails() throws HouseNotFoundException {
		when(rentRepository.findByHouseHouseId(2000L)).thenReturn(tenant);
		Tenant tenantFromDb = rentService.getTenantDetails(2000L);
		assertEquals(tenant,tenantFromDb);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void testGetTenantDetailsWithNoHouse() throws HouseNotFoundException {
		when(rentRepository.findByHouseHouseId(2000L)).thenReturn(null);
		rentService.getTenantDetails(2000L);
	}
	
	@Test
	public void testDeleteTenant() throws HouseNotFoundException, ApplicationException {
		tenant.setTenantId(1000L);
		house.setTenant(tenant);
		when(rentRepository.findByHouseHouseId(2000L)).thenReturn(tenant);
		when(housingRepository.findByTenantTenantId(1000L)).thenReturn(house);
		rentService.deleteTenant(2000L, 1000L);
		verify(contractRepository,times(1)).deleteById(5000);
		verify(rentRepository,times(1)).deleteById(1000L);
	}
	
	@Test(expected = ApplicationException.class)
	public void testDeleteTenantWithNoTenant() throws HouseNotFoundException, ApplicationException {
		when(rentRepository.findByHouseHouseId(2000L)).thenReturn(tenant);
		rentService.deleteTenant(2000L, 1000L);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void testDeleteTenantWithNoHouse() throws HouseNotFoundException, ApplicationException {
		rentService.deleteTenant(2000L, 1000L);
	}
	
	@Test
	public void testUpdateTenant() throws HouseNotFoundException, ApplicationException {
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		when(rentRepository.findById(1000L)).thenReturn(Optional.of(tenant));
		rentService.updateTenantDetails(1000L, tenant, 2000L);
		verify(rentRepository,times(1)).save(tenant);
	}
	
	@Test
	public void testUpdateTenantWithTenantStatusInactive() throws HouseNotFoundException, ApplicationException {
		tenant.setTenantStatus(TenantStatus.INACTIVE);
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		when(rentRepository.findById(1000L)).thenReturn(Optional.of(tenant));
		rentService.updateTenantDetails(1000L, tenant, 2000L);
		verify(rentRepository,times(1)).save(tenant);
		verify(housingRepository,times(1)).save(house);
	}
	
	@Test(expected = HouseNotFoundException.class)
	public void testUpdateTenantWithNoHouse() throws HouseNotFoundException, ApplicationException {
		rentService.updateTenantDetails(1000L, tenant, 2000L);
	}
	
	@Test(expected = ApplicationException.class)
	public void testUpdateTenantWithNoTenat() throws HouseNotFoundException, ApplicationException {
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		rentService.updateTenantDetails(1000L, tenant, 2000L);
	}
	
	@Test(expected = ApplicationException.class)
	public void testGetHouseDetails() throws HouseNotFoundException, ApplicationException {
		when(housingRepository.findById(2000L)).thenReturn(Optional.of(house));
		rentService.updateTenantDetails(1000L, tenant, 2000L);
	}

}
