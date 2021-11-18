package com.paymybuddy.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;
import com.paymybuddy.repository.entity.User;

import lombok.AllArgsConstructor;

/**
 * Instantiates a new connection repository impl.
 *
 * @param jdbcTemplate the jdbc template
 */
@AllArgsConstructor
@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ConnectionRepositoryImpl.class);

	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The Constant insert. */
	private static final String insert = "insert into connection (description, user_target_id) values (?,?)";
	
	/** The Constant addToUser. */
	private static final String addToUser = "insert into user_connections (connections_id, user_id) " + " values (?,?)";
	
	/** The Constant addFinancialTransaction. */
	private static final String addFinancialTransaction = "insert into connection_financial_transactions (connection_id, financial_transactions_id) values (?,?)";
	
	/** The Constant select. */
	private static final String select = " select con.id as id, description, user_target_id, email from connection con"
			+ " inner join user_connections user_con " + " on user_con.connections_id = con.id "
			+ " inner join user on user.id = user_target_id" + " where user_con.user_id = ?";

	/** The Constant selectById. */
	private static final String selectById = "select id, description, user_target_id from connection where id = ?";

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the connection
	 */
	@Override
	public Connection findById(Long id) {
		logger.info("recuperation d'une connection par son identifiant : {}", id);
		return jdbcTemplate.queryForObject(selectById, new RowMapper<Connection>() {

			@Override
			public Connection mapRow(ResultSet rs, int rowNum) throws SQLException {
				Connection connection = new Connection();
				connection.setId(rs.getLong("id"));
				connection.setDescription(rs.getString("description"));
				User user = new User();
				user.setEmail("email");
				user.setId(rs.getLong("user_target_id"));
				connection.setUserTarget(user);
				return connection;
			}

		}, id);
	}

	/**
	 * Creates the.
	 *
	 * @param userIdTarget the user id target
	 * @param description the description
	 * @return the connection
	 */
	@Override
	public Connection create(Long userIdTarget, String description) {
		logger.info("creation d'une connection à l'utilisateur {} avec la description : {}", userIdTarget, description);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connectionSql -> {
			PreparedStatement ps = connectionSql.prepareStatement(insert, new String[] { "ID" });
			ps.setString(1, description);
			ps.setLong(2, userIdTarget);
			return ps;
		}, keyHolder);
		Connection connection = new Connection();
		connection.setId(keyHolder.getKey().longValue());
		connection.setDescription(description);
		connection.setUserTarget(User.builder().id(userIdTarget).build());
		return connection;
	}

	/**
	 * Adds the to user.
	 *
	 * @param user the user
	 * @param connection the connection
	 */
	@Override
	public void addToUser(User user, Connection connection) {
		logger.info("affectation de la connection avec l'identifiant {} pour l'utilisateur {}", connection.getId(), user.getEmail());
		jdbcTemplate.update(connectionSql -> {
			PreparedStatement ps = connectionSql.prepareStatement(addToUser, new String[] { "ID" });
			ps.setLong(1, connection.getId());
			ps.setLong(2, user.getId());
			return ps;
		});
	}

	/**
	 * Gets the all connection.
	 *
	 * @param user the user
	 * @return the all connection
	 */
	@Override
	public List<Connection> getAllConnection(User user) {
		logger.info("récupération des connections pour l'utilisateur {}", user.getEmail());
		return jdbcTemplate.query(select, new RowMapper<Connection>() {

			@Override
			public Connection mapRow(ResultSet rs, int rowNum) throws SQLException {
				Connection connection = new Connection();
				connection.setId(rs.getLong("id"));
				connection.setDescription(rs.getString("description"));
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setId(rs.getLong("user_target_id"));
				connection.setUserTarget(user);
				return connection;
			}

		}, user.getId());
	}

	/**
	 * Adds the financial transaction.
	 *
	 * @param connection the connection
	 * @param financialTransaction the financial transaction
	 */
	@Override
	public void addFinancialTransaction(Connection connection, FinancialTransaction financialTransaction) {
		logger.info("ajout d'une transaction financiere d'un montant de {} à la connection avec l'identifiant {}", financialTransaction.getAmount(), connection.getId());
		jdbcTemplate.update(connectionSql -> {
			PreparedStatement ps = connectionSql.prepareStatement(addFinancialTransaction);
			ps.setLong(1, connection.getId());
			ps.setLong(2, financialTransaction.getId());
			return ps;
		});
	}

}
