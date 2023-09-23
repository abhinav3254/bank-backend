package com.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dao.UserDao;


/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service loads user details from the database based on the provided username.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	com.pojo.User user;
	
	
	/**
     * Load user details by username.
     *
     * @param username The username provided during authentication.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException Thrown if the user is not found in the database.
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user = userDao.getUserByUsername(username);
		if (user != null) {
			return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User Not Find By the email"+username);
		}
	}
	
	
	/**
     * Get the user details associated with the currently authenticated user.
     *
     * @return User details for the currently authenticated user.
     */
	public com.pojo.User getUserDetails() {
		return user;
	}

}