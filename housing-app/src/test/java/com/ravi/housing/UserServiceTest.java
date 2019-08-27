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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.ravi.housing.domain.User;
import com.ravi.housing.exception.ApplicationException;
import com.ravi.housing.exception.UserNotFoundException;
import com.ravi.housing.repo.UserRepository;
import com.ravi.housing.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	private User user;

	private List<User> users;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		users = new ArrayList<User>();
		user.setFirstName("Ravi");
		user.setLastName("Jagarlapudi");
		user.setEmailAddress("ravi@gmail.com");
		user.setMobileNumber("9632569878");
		users.add(user);
	}

	@Test
	public void testAddUser() throws ApplicationException {
		userService.addUser(user);
		verify(userRepository, times(1)).save(user);
	}

	@Test(expected = ApplicationException.class)
	public void testAddUserwithsameEmail() throws ApplicationException {
		when(userRepository.findByEmailAddress(Mockito.anyString())).thenReturn(user);
		userService.addUser(user);
	}

	@Test
	public void testGetAllUsers() {
		when(userRepository.findAll()).thenReturn(users);
		List<User> usersTest = userService.getAllusers();
		assertEquals(usersTest.size(), 1);
	}

	@Test
	public void testGetUserDetails() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.of(user));
		User testUser = userService.getUserDetails(1000L);
		assertEquals(testUser.getEmailAddress(), user.getEmailAddress());
	}

	@Test(expected = UserNotFoundException.class)
	public void testGetUserDetailsWithNoUserPresent() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.empty());
		userService.getUserDetails(1000L);
	}

	@Test
	public void testDeleteUser() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.of(user));
		userService.deleteUserDetails(1000L);
		verify(userRepository, times(1)).deleteById(1000L);
	}

	@Test(expected = UserNotFoundException.class)
	public void testDeleteUserWhenNoUserAVailable() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.empty());
		userService.deleteUserDetails(1000L);
	}

	@Test
	public void testUpdateUser() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.of(user));
		userService.updateUser(user, 1000L);
		verify(userRepository, times(1)).save(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUpdateUserWhenNoUserAvailable() throws UserNotFoundException {
		when(userRepository.findById(1000L)).thenReturn(Optional.empty());
		userService.updateUser(user, 1000L);
	}

}
