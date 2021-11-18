package com.paymybuddy.service;

import java.util.List;

import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.form.ConnectionForm;
import com.paymybuddy.form.FinancialTransactionForm;
import com.paymybuddy.repository.entity.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the user.
	 *
	 * @param password the password
	 * @return the user
	 */
	UserDetail getUser(String password);

	/**
	 * Creates the user.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the user
	 */
	User create(String email, String password);

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<User> getAll();

	/**
	 * Adds the financialtransaction.
	 *
	 * @param userDetail the user detail
	 * @param financialTransactionForm the financial transaction form
	 * @return the user detail
	 */
	UserDetail addFinancialtransaction(UserDetail userDetail, FinancialTransactionForm financialTransactionForm);

	/**
	 * Adds the connection.
	 *
	 * @param userDetail the user detail
	 * @param connectionForm the connection form
	 * @return the user detail
	 */
	UserDetail addConnection(UserDetail userDetail, ConnectionForm connectionForm);

}