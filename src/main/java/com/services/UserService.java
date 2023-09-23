package com.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.pojo.User;

/**
 * This interface defines the contract for user-related services, including user registration and login.
 */
public interface UserService {
	
	/**
     * Register a new user with the provided user information.
     *
     * @param map A map containing user registration information, such as "username", "name", "email", etc.
     * @return A ResponseEntity containing a String response, typically indicating the result of the registration attempt.
     */
	public ResponseEntity<String> addUser(Map<String, String>map);
	
	/**
     * Authenticate and log in a user with the provided login credentials.
     *
     * @param map A map containing user login credentials, such as "username" and "password".
     * @return A ResponseEntity containing a String response, typically indicating the result of the login attempt.
     */
	public ResponseEntity<String> logIn(Map<String, String>map);
	
	
	/**
	 * Get the user's profile information.
	 *
	 * @return A ResponseEntity containing the user's profile information as a User object.
	 */
	public ResponseEntity<User> getProfile();
	
	public ResponseEntity<List<User>> getAllManager();
	
	public ResponseEntity<List<User>> getAllUsers();
	
	public ResponseEntity<String> approveManager(String userId);
	
	public ResponseEntity<String> declineManager(String userId);
	
	public ResponseEntity<List<User>> getAllUnapprovedManager();
	

}
