package com.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pojo.User;

/**
 * This interface defines the REST API endpoints related to user operations.
 */
@RequestMapping("/user")
public interface UserRest {
	
	
	/**
     * Endpoint for user registration (sign-up).
     *
     * @param map A map containing user registration information, such as "username", "name", "email", etc.
     * @return A ResponseEntity containing a String response, typically indicating the result of the registration attempt.
     */
	@PostMapping("/signup")
	public ResponseEntity<String> addUser(@RequestBody(required = true) Map<String,String>map);
	
	
	/**
     * Endpoint for user login.
     *
     * @param map A map containing user login credentials, such as "username" and "password".
     * @return A ResponseEntity containing a String response, typically indicating the result of the login attempt.
     */
	@PostMapping("/login")
	public ResponseEntity<String> logIn(@RequestBody(required = true) Map<String,String>map);
	
	/**
	 * Retrieve the user's profile information.
	 *
	 * @return A ResponseEntity containing the user's profile information as a User object.
	 */
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile();
	
	
	/* -------------- ADMIN SECTION START FROM HERE ----------------- */
	
	@GetMapping("/admin/not-approved-manager")
	public ResponseEntity<List<User>> getAllUnapprovedManager();
	
	@GetMapping("/admin/all-manager")
	public ResponseEntity<List<User>> getAllManager();
	
	@GetMapping("/admin/all-users")
	public ResponseEntity<List<User>> getAllUsers();
	
	@GetMapping("/admin/approveManager")
	public ResponseEntity<String> approveManager(@RequestParam(required = true) String userId);
	
	@GetMapping("/admin/declineManager")
	public ResponseEntity<String> declineManager(@RequestParam(required = true) String userId);
	
	
}
