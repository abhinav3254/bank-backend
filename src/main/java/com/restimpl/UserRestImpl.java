package com.restimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.extra.Extra;
import com.pojo.User;
import com.rest.UserRest;
import com.services.UserService;


/**
 * Controller class that implements the UserRest interface and handles user-related REST API endpoints.
 */
@RestController
public class UserRestImpl implements UserRest {

	@Autowired
	private UserService userService;

	/**
     * Endpoint for user registration (sign-up).
     *
     * @param map A map containing user registration information, such as "username", "name", "email", etc.
     * @return A ResponseEntity containing a String response, typically indicating the result of the registration attempt.
     */
	@Override
	public ResponseEntity<String> addUser(Map<String, String> map) {
		try {
			// Delegate the user registration process to the UserService and return the result.
			return userService.addUser(map);
		} catch (Exception e) {
			// Handle any exceptions that may occur during user registration and log the error.
			e.printStackTrace();
		}
		// Return an INTERNAL_SERVER_ERROR response if an exception occurs.
		return new ResponseEntity<String>(Extra.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
     * Endpoint for user login.
     *
     * @param map A map containing user login credentials, such as "username" and "password".
     * @return A ResponseEntity containing a String response, typically indicating the result of the login attempt.
     */
	@Override
	public ResponseEntity<String> logIn(Map<String, String> map) {
		try {
			// Delegate the user login process to the UserService and return the result.
			return userService.logIn(map);
		} catch (Exception e) {
			// Handle any exceptions that may occur during user login and log the error.
			e.printStackTrace();
		}
		// Return an INTERNAL_SERVER_ERROR response if an exception occurs.
		return new ResponseEntity<String>(Extra.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Retrieve the user's profile information.
	 *
	 * @return A ResponseEntity containing the user's profile information as a User object, or
	 *         an INTERNAL_SERVER_ERROR response if an exception occurs during the retrieval process.
	 */
	@Override
	public ResponseEntity<User> getProfile() {
		try {
			return userService.getProfile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllManager() {
		try {
			return userService.getAllManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			return userService.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
