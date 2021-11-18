package com.paymybuddy.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.paymybuddy.controller.UserController;
import com.paymybuddy.dto.ConnectionDTO;
import com.paymybuddy.dto.FinancialTransactionDTO;
import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.User;

/**
 * The Class UserMapper.
 */
@Component
public class UserMapper {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(UserMapper.class);
	
	/**
	 * Map.
	 *
	 * @param user the user
	 * @return the user detail
	 */
	public UserDetail map(User user) {
		logger.info("mapping user vers userDetail");
		List<Connection> connections = user.getConnections();
		List<FinancialTransactionDTO> FinancialTransactionDTOs = new ArrayList<FinancialTransactionDTO>();
		connections.forEach(connection -> {
			if (connection.getFinancialTransactions() == null)
				return;
			connection.getFinancialTransactions().forEach(financialTransaction -> {
				FinancialTransactionDTOs.add(FinancialTransactionDTO.builder()
						.userTargetEmail(connection.getUserTarget().getEmail()).amount(financialTransaction.getAmount())
						.description(connection.getDescription()).build());

			});
		});
		List<ConnectionDTO> connectionDTOs = connections.stream().map(connection -> ConnectionDTO.builder()
				.description(connection.getDescription()).id(connection.getId()).build()).collect(Collectors.toList());
		return UserDetail.builder().user(user).connections(connectionDTOs)
				.financialTransactions(FinancialTransactionDTOs).build();
	}

}
