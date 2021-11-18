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

import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.repository.entity.User;

import lombok.AllArgsConstructor;

/**
 * Instantiates a new user repository impl.
 *
 * @param jdbcTemplate the jdbc template
 */
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	/** The jdbc template. */
	private final JdbcTemplate jdbcTemplate;

	/** The Constant insert. */
	private static final String insert = "insert into user (email, password) values (?,?)";
	
	/** The Constant select. */
	public static final String select = "select id, email, password from user where password = '%s'";
	
	/** The Constant selectAll. */
	public static final String selectAll = "select id, email from user";

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	public User createUser(User user) {
		logger.info("création de l'utilisateur {}", user.getEmail());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(insert, new String[] { "ID" });
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			return ps;
		}, keyHolder);

		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	/**
	 * Connect user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@Override
	public User connectUser(User user) {
		logger.info("connection de l'utilisateur {}", user.getEmail());
		return jdbcTemplate.queryForObject(String.format(select, user.getPassword()), new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setEmail(rs.getString("email"));
				return user;
			}

		});
	}

	/**
	 * Select all.
	 *
	 * @return the list
	 */
	public List<User> selectAll() {
		logger.info("récupération de tous les utilisateurs");
		
		return jdbcTemplate.query(selectAll, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setEmail(rs.getString("email"));
				return user;
			}

		});
	}

}
