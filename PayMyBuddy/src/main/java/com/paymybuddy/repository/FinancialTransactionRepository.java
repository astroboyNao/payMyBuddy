package com.paymybuddy.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;
import com.paymybuddy.repository.entity.User;

/**
 * The Interface FinancialTransactionRepository.
 */
@Repository
public interface FinancialTransactionRepository {
	
	/**
	 * Creates the.
	 *
	 * @param usrSource the usr source
	 * @param connection the connection
	 * @param amount the amount
	 * @return the financial transaction
	 */
	public FinancialTransaction create(User usrSource, Connection connection, Float amount);

	/**
	 * Gets the all financial transaction.
	 *
	 * @param connections the connections
	 * @return the all financial transaction
	 */
	public List<FinancialTransaction> getAllFinancialTransaction(List<Connection> connections);
}
