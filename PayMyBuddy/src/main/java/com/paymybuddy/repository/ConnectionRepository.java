package com.paymybuddy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;
import com.paymybuddy.repository.entity.User;

/**
 * The Interface ConnectionRepository.
 */
@Repository
public interface ConnectionRepository {
	
	/**
	 * Creates the.
	 *
	 * @param userId the user id
	 * @param description the description
	 * @return the connection
	 */
	Connection create(Long userId, String description);

	/**
	 * Adds the to user.
	 *
	 * @param user the user
	 * @param connection the connection
	 */
	void addToUser(User user, Connection connection);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the connection
	 */
	Connection findById(Long id);

	/**
	 * Gets the all connection.
	 *
	 * @param user the user
	 * @return the all connection
	 */
	List<Connection> getAllConnection(User user);

	/**
	 * Adds the financial transaction.
	 *
	 * @param connection the connection
	 * @param financialTransaction the financial transaction
	 */
	void addFinancialTransaction(Connection connection, FinancialTransaction financialTransaction);
}
