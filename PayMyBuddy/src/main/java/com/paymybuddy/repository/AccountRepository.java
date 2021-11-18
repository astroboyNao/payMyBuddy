package com.paymybuddy.repository;

import com.paymybuddy.repository.entity.Account;
import com.paymybuddy.repository.entity.User;

/**
 * The Interface AccountRepository.
 */
public interface AccountRepository {

	/**
	 * Creates the.
	 *
	 * @param amount the amount
	 * @return the account
	 */
	Account create(Long amount);

	/**
	 * Select.
	 *
	 * @param user the user
	 * @return the account
	 */
	Account select(User user);

}