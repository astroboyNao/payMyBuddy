package com.paymybuddy.repository;

import java.util.List;

import com.paymybuddy.repository.entity.User;

/**
 * The Interface UserRepository.
 */
public interface UserRepository {
	
	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User createUser(User user);

	/**
	 * Connect user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User connectUser(User user);

	/**
	 * Select all.
	 *
	 * @return the list
	 */
	public List<User> selectAll();
}
