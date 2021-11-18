package com.paymybuddy.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.paymybuddy.mapper.UserMapper;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.entity.Account;
import com.paymybuddy.repository.entity.User;

import lombok.AllArgsConstructor;

/**
 * Instantiates a new account repository impl.
 *
 * @param jdbcTemplate the jdbc template
 */
@AllArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);
	
	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The query insert. */
	private static final String insert = "insert into account (amount) values (?)";
	
	/** The query select. */
	private static final String select = "select id, amount from account where id = ?";

	/**
	 * Creates the.
	 *
	 * @param amount the amount
	 * @return the account
	 */
	@Override
	public Account create(Long amount) {
		logger.info("creation d'un compte avec un solde de {}", amount);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(insert, new String[] { "ID" });
			ps.setLong(1, amount);
			return ps;
		}, keyHolder);
		Account account = new Account();
		account.setAmount(0L);
		account.setId(keyHolder.getKey().longValue());
		return account;
	}

	/**
	 * Select.
	 *
	 * @param user the user
	 * @return the account
	 */
	@Override
	public Account select(User user) {
		logger.info("récupération d'un compte pour l'utilisateur {}", user.getEmail());
		
		return jdbcTemplate.queryForObject(select, new RowMapper<Account>() {

			@Override
			public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
				Account account = new Account();
				account.setId(rs.getLong("id"));
				account.setAmount(rs.getLong("amount"));
				return account;
			}

		}, user.getId());
	}

}
