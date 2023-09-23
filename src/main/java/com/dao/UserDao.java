package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.User;

/**
 * Repository interface for accessing user-related data in the database.
 */
public interface UserDao extends JpaRepository<User, Integer> {
	
	/**
     * Retrieve a user entity by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The User entity with the specified username.
     */
	@Query(nativeQuery = true,value = "select * from user where username =:username")
	public User getUserByUsername(String username);
	
	@Query(value = "SELECT u FROM User u WHERE u.role LIKE '%manager%'")
	public List<User> getAllManager();
	
	@Query(value = "SELECT u FROM User u WHERE u.role LIKE '%user%'")
	public List<User> getAllUser();

}
