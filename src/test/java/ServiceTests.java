import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;


public class ServiceTests {
	
	public static User user;
	public static User userToAddDelete;
	public static Reimbursement reimbursement;
	public static Reimbursement reimbursementToAdd;
	public ReimbursementService reimbursementService = new ReimbursementService();
	public UserService userService = new UserService();
	public LoginService loginService = new LoginService();
	public UserDTO userDTO = new UserDTO();
	public static Logger log = LoggerFactory.getLogger(ServiceTests.class);

	@BeforeEach
	public void setValues() {
		user = new User(1, "username", 1216985755, "Test", "Tester", "email", 2);
		reimbursement = new Reimbursement(6, 543, "2021-10-28T06:13:39.924Z", null, "jUnit Test", user, null, 1, 1);
		userDTO.password="password";
		userDTO.username="username";
		userToAddDelete = new User("temp", 3556308, "Justin", "Chang", "email", 2);
		reimbursementToAdd = new Reimbursement(7, 0, "2021-10-31T06:13:39.924Z", null, "jUnit Test", user, null, 1, 1);
		reimbursementService.updateReimbursement(reimbursement);
	}
	
	@Test
	public void testLoggingIn() {
		log.info("In loggingIn test");
		assertTrue(loginService.login(userDTO));
	}
	
	@Test
	public void testGetUsers() {
		log.info("In getUsers test");
		List<User> users = userService.getAllUsers();
		assertTrue(!users.isEmpty());
	}
	
	@Test
	public void testGetUserByUsername() {
		log.info("In getUserByUsername test");
		User user2 = userService.getByUsername(userDTO.username);
		assertEquals(user, user2);
	}
	
	@Test
	public void testAddandDeleteUser() {
		log.info("In testAddandDeleteUser");
		userService.addUser(userToAddDelete);
		assertTrue(userService.deleteUser("temp"));
	}
	
	@Test
	public void testGetAllReimbursements() {
		log.info("In testGetAllReimbursements");
		List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
		assertTrue(!reimbursements.isEmpty());
	}
	
	@Test
	public void testGetReimbursementByID() {
		log.info("In testGetReimbursementByID");
		assertTrue(reimbursementService.getReimbursement(reimbursement.getId()) != null);
		
	}
	
	@Test
	public void testAddReimbursement() {
		log.info("In testAddReimbursement");
		assertTrue(reimbursementService.addReimbursement(reimbursementToAdd));
	}
	
	@Test
	public void testUpdateReimbursement() {
		log.info("In testUpdateReimbursement");
		assertTrue(reimbursementService.updateReimbursement(new Reimbursement(
				6, 543, "2021-10-28T06:13:39.924Z", "2021-10-31T06:13:39.924Z", "jUnit Test", user, user, 2, 1)));
	}
}
