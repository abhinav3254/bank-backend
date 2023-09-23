package com.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.extra.Extra;
import com.jwt.JwtFilter;
import com.jwt.JwtUtils;
import com.pojo.User;
import com.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	public ResponseEntity<String> addUser(Map<String, String> map) {
		try {
			if (Objects.isNull(configUser(map))) {
				return new ResponseEntity<String>("INVALID DATA", HttpStatus.BAD_REQUEST);
			} else {
				// Here we need to check if username is already taken or not
				User user = configUser(map);

				String username = user.getUsername();

				if (Objects.isNull(userDao.getUserByUsername(username))) {
					userDao.save(user);
					return new ResponseEntity<String>("DONE", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("USER ALREADY EXISTS", HttpStatus.BAD_REQUEST);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(Extra.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Configures and creates a new User object based on the provided map of user
	 * information.
	 *
	 * @param map A map containing user information, including "username", "name",
	 *            "email", "password", "phone", "address", and "gender".
	 * @return A User object configured with the provided information, or null if an
	 *         exception occurs.
	 */
	private User configUser(Map<String, String> map) {
		try {

			// Extract user information from the map
			String username = map.get("username");
			String name = map.get("name");
			String email = map.get("email");
			String password = map.get("password");
			String phone = map.get("phone");
			String address = map.get("address");
			String gender = map.get("gender");

			// Create a new User object
			User user = new User();

			// Set user properties
			user.setUsername(username);
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhone(phone);
			user.setAddress(address);
			user.setGender(gender);
			// Set default user role and status

			// Role to be changed from USER to CUSTOMER
			user.setRole("USER");
			user.setStatus("FALSE");

			// Set the user's last login and registration date to the current date and time
			user.setLastLogin(new Date());
			user.setRegisterDate(new Date());

			// Return the configured User object
			return user;

		} catch (Exception e) {
			// Handle any exceptions that may occur during configuration
			e.printStackTrace();
		}
		// Return null if an exception occurs
		return null;
	}

	/**
	 * Authenticate and log in a user with the provided login credentials.
	 *
	 * @param map A map containing user login credentials, such as "username" and
	 *            "password".
	 * @return A ResponseEntity containing a String response, typically indicating
	 *         the result of the login attempt.
	 */
	@Override
	public ResponseEntity<String> logIn(Map<String, String> map) {
		try {
			// Attempt to configure user login using provided credentials
			User user = configLogIn(map);
			if (Objects.isNull(user)) {
				// If user is null, the login data is invalid, return a BAD_REQUEST response.
				return new ResponseEntity<String>("INVALID DATA", HttpStatus.BAD_REQUEST);
			} else {
				// Generate a JWT token for the user's successful login and return an OK
				// response.
				if (user.getStatus().equalsIgnoreCase("false")) {
					return new ResponseEntity<String>("WAIT FOR APPROVAL",HttpStatus.LOCKED);
				} else {
					return new ResponseEntity<String>(jwtUtils.generateToken(user.getUsername(), user.getRole()),
							HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			// Handle any exceptions that may occur during the login process and log the
			// error.
			e.printStackTrace();
		}
		// Return an INTERNAL_SERVER_ERROR response if an exception occurs.
		return new ResponseEntity<String>(Extra.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Configures and validates user login based on the provided credentials.
	 *
	 * @param map A map containing user login credentials, such as "username" and
	 *            "password".
	 * @return A User object if the login is successful, or null if the login is
	 *         invalid.
	 */
	private User configLogIn(Map<String, String> map) {

		String username = map.get("username");
		String password = map.get("password");

		if (username.isEmpty() || password.isEmpty()) {
			// If either username or password is empty, return null indicating invalid data.
			return null;
		} else {
			// Attempt to retrieve the user from the userDao by username.
			User user = userDao.getUserByUsername(username);

			if (user != null && user.getPassword().equals(password)) {
				// If the user exists and the provided password matches, the login is valid.
				return user;
			} else {
				// If the user doesn't exist or the password doesn't match, return null
				// indicating invalid login.
				return null;
			}

		}

	}

	/**
	 * Retrieve the user's profile information.
	 *
	 * @return A ResponseEntity containing the user's profile information as a User
	 *         object with an OK status if successful, or an INTERNAL_SERVER_ERROR
	 *         response if an exception occurs during the retrieval process.
	 */
	@Override
	public ResponseEntity<User> getProfile() {
		try {
			// Obtain the currently authenticated user from the security context
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);

			// Retrieve the user's profile information based on the extracted username
			User user = userDao.getUserByUsername(username);

			// Return the user's profile as a ResponseEntity with an OK status
			return new ResponseEntity<User>(user, HttpStatus.OK);

		} catch (Exception e) {
			// Handle any exceptions that may occur during the retrieval process and log the
			// error.
			e.printStackTrace();
		}
		// Return an INTERNAL_SERVER_ERROR response if an exception occurs.
		return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllManager() {
		try {

			if (jwtFilter.isAdmin()) {

				List<User> listManager = userDao.getAllManager();

				return new ResponseEntity<List<User>>(listManager, HttpStatus.OK);

			} else {
				return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			if (jwtFilter.isAdmin() || jwtFilter.isManager()) {

				List<User> listUsers = userDao.getAllUser();

				return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);

			} else {
				return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> approveManager(String userId1) {
		try {
			
			if (jwtFilter.isAdmin()) {
				Integer userId = Integer.parseInt(userId1);
				
				userDao.setStatusTrueOfManager(userId);
				
				return new ResponseEntity<String>("UPDATED",HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> declineManager(String userId1) {
		try {
			
			if (jwtFilter.isAdmin()) {
				Integer userId = Integer.parseInt(userId1);
				
				userDao.setStatusFalseOfManager(userId);
				
				return new ResponseEntity<String>("UPDATED",HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllUnapprovedManager() {
		try {
			if (jwtFilter.isAdmin()) {
				List<User> listOfUnapprovedManager = userDao.getAllUnapprovedManager();
				return new ResponseEntity<List<User>>(listOfUnapprovedManager,HttpStatus.OK);
			} else {
				return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
