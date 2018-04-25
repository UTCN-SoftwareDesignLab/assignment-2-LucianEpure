package tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dto.UserDto;
import entity.Book;
import entity.Role;
import entity.User;
import repository.RoleRepository;
import repository.UserRepository;

import service.user.AuthenticationService;
import service.user.AuthenticationServiceImplementation;
import service.user.UserService;
import service.user.UserServiceImplementation;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

	 public static final String TEST_USERNAME = "test@gmail.com";
	 public static final String TEST_PASSWORD = "TestPassword1!";
	 public static final String TEST_USERNAME_UPDATED = "testUdated@gmail.com";
	 public static final String TEST_PASSWORD_UPDATED = "TestPasswordUpdated1!";
	UserService userService;
	AuthenticationService authenticationService;
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@Before
	public void setup(){
		authenticationService = new AuthenticationServiceImplementation(userRepository,roleRepository);
		userService = new UserServiceImplementation(userRepository, authenticationService);
		Role roleAdmin = new Role();
		roleAdmin.setRoleName("administrator");
		Role roleUser = new Role();
		roleUser.setRoleName("regUser");
		User user = new User();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		Mockito.when(roleRepository.findByRoleName("administrator")).thenReturn(roleAdmin);
		Mockito.when(roleRepository.findByRoleName("regUser")).thenReturn(roleUser);
		Mockito.when(userRepository.getOne(1)).thenReturn(user);
		Mockito.when(userRepository.findByUsernameAndPassword(TEST_USERNAME, authenticationService.encodePassword(TEST_PASSWORD))).thenReturn(user);
		//Mockito.when(bookRepository.findByQuantity(0)).thenReturn(books);
		//Mockito.when(bookRepository.save(book)).thenReturn(true);
		
	}
	
	@Test()
	public void registerAdmin(){
		UserDto user = new UserDto();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		Assert.assertTrue(authenticationService.registerAdmin(user).getResult());
	}
	
	@Test()
	public void registerUser(){
		UserDto user = new UserDto();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		Assert.assertTrue(authenticationService.registerUser(user).getResult());
	}
	
	@Test()
	public void login(){
		UserDto user = new UserDto();
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		Assert.assertNotNull(authenticationService.login(user));
	}
	
	@Test()
	public void findAll(){
		List<User> users = userService.findAll();
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 0);
	}
	
	@Test()
	public void update(){
		UserDto user = new UserDto();
		user.setId(1);
		user.setUsername(TEST_USERNAME);
		user.setPassword(TEST_PASSWORD);
		Assert.assertTrue(userService.update(user).getResult());
	}
	
	
}
