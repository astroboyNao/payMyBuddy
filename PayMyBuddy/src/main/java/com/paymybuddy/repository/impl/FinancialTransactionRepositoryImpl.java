package com.paymybuddy.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.FinancialTransactionRepository;
import com.paymybuddy.repository.entity.Account;
import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;
import com.paymybuddy.repository.entity.User;

import lombok.AllArgsConstructor;
import lombok.var;

/**
 * Instantiates a new financial transaction repository impl.
 *
 * @param jdbcTemplate the jdbc template
 */
@AllArgsConstructor
@Repository
public class FinancialTransactionRepositoryImpl implements FinancialTransactionRepository {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(FinancialTransactionRepositoryImpl.class);
	
	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The Constant insert. */
	private static final String insert = "insert into financial_transaction (amount, account_source_id, account_destination_id) values (?,?,?)";
	
	/** The Constant select. */
	public static final String select = "select ft.id as id, amount, account_destination_id, con.id as con_id from financial_transaction ft "
			+ " inner join connection_financial_transactions conft" + " on conft.financial_transactions_id = ft.id "
			+ " inner join connection con on connection_id = con.id " + " where account_source_id in (%s)";

	/**
	 * Creates the.
	 *
	 * @param usrSource the usr source
	 * @param connection the connection
	 * @param amount the amount
	 * @return the financial transaction
	 */
	public FinancialTransaction create(User usrSource, Connection connection, Float amount) {
		logger.info("création d'un transaction financiere pour l'utilisateur {} pour la connection avec l'identifiant {} d'un montant de {}", usrSource.getEmail(), connection.getId(), amount);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connectionSql -> {
			PreparedStatement ps = connectionSql.prepareStatement(insert, new String[] { "ID" });
			ps.setFloat(1, amount);
			ps.setLong(2, usrSource.getAccount().getId());
			ps.setLong(3, connection.getUserTarget().getId());
			return ps;
		}, keyHolder);
		FinancialTransaction financialTransaction = new FinancialTransaction();
		financialTransaction.setId(keyHolder.getKey().longValue());
		financialTransaction.setAmount(amount);
		financialTransaction.setAccountSource(Account.builder().id(usrSource.getAccount().getId()).build());
		financialTransaction.setAccountDestination(Account.builder().id(connection.getUserTarget().getId()).build());
		return financialTransaction;
	}

	/**
	 * Gets the all financial transaction.
	 *
	 * @param connections the connections
	 * @return the all financial transaction
	 */
	public List<FinancialTransaction> getAllFinancialTransaction(List<Connection> connections) {	
		logger.info("récupération de toutes les transactions financieres pour toutes les connections transmises");
		connections.stream()
				.forEach(connection -> connection.setFinancialTransactions(new ArrayList<FinancialTransaction>()));
		var idConnections = connections.stream().map(Connection::getId).collect(Collectors.toList()).toArray();

		String query = String.format(select, idConnections);

		return jdbcTemplate.query(query, new RowMapper<FinancialTransaction>() {

			@Override
			public FinancialTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
				FinancialTransaction financialTransaction = new FinancialTransaction();
				financialTransaction.setId(rs.getLong("id"));
				financialTransaction.setAmount(rs.getFloat("amount"));
				financialTransaction
						.setAccountDestination(Account.builder().id(rs.getLong("account_destination_id")).build());
				Long idConnection = rs.getLong("con_id");
				Connection currentConnection = connections.stream()
						.filter(con -> con.getId().compareTo(idConnection) == 0).findFirst().get();
				currentConnection.getFinancialTransactions().add(financialTransaction);
				return financialTransaction;
			}

		});
	}

}
