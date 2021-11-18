package com.paymybuddy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.form.ConnectionForm;
import com.paymybuddy.form.FinancialTransactionForm;
import com.paymybuddy.mapper.UserMapper;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.FinancialTransactionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.repository.entity.Account;
import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;
import com.paymybuddy.repository.entity.User;
import lombok.AllArgsConstructor;

/**
 * The Class UserServiceImpl.
 */
@Service

/**
 * Instantiates a new user service impl.
 *
 * @param accountRepository the account repository
 * @param userRepository the user repository
 * @param connectionRepository the connection repository
 * @param transactionManager the transaction manager
 * @param financialTransactionRepository the financial transaction repository
 * @param userMapper the user mapper
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	/** The account repository. */
	private AccountRepository accountRepository;
	
	/** The user repository. */
	private UserRepository userRepository;
	
	/** The connection repository. */
	private ConnectionRepository connectionRepository;
	
	/** The transaction manager. */
	private PlatformTransactionManager transactionManager;
	
	/** The financial transaction repository. */
	private FinancialTransactionRepository financialTransactionRepository;
	
	/** The user mapper. */
	private UserMapper userMapper;

	/**
	 * Gets the user.
	 *
	 * @param password the password
	 * @return the user
	 */
	@Override
	public UserDetail getUser(String password) {
		logger.info("connection d'un utilisateur");
		User user = new User();
		user.setPassword(password);
		user = userRepository.connectUser(user);
		if(user == null) throw new RemoteAccessException("user ou password incorrect");
		Account account = accountRepository.select(user);
		user.setAccount(account);

		List<Connection> connections = connectionRepository.getAllConnection(user);

		user.setConnections(connections);

		financialTransactionRepository.getAllFinancialTransaction(connections);

		return userMapper.map(user);
	}

	/**
	 * Creates the.
	 *
	 * @param email the email
	 * @param password the password
	 * @return the user
	 */
	@Override
	public User create(String email, String password) {
		logger.info("creation de l'utilisateur {}", email);
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		User user = template.execute(new TransactionCallback<User>() {

			@Override
			public User doInTransaction(TransactionStatus status) {

				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user = userRepository.createUser(user);

				Account account = accountRepository.create(0L);
				user.setAccount(account);

				return user;
			}

		});

		return user;

	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<User> getAll() {
		logger.info("récupération de tous les utilisateurs");
		return this.userRepository.selectAll();
	}

	/**
	 * Adds the financialtransaction.
	 *
	 * @param userDetail the user detail
	 * @param financialTransactionForm the financial transaction form
	 * @return the user detail
	 */
	@Override
	public UserDetail addFinancialtransaction(UserDetail userDetail,
			FinancialTransactionForm financialTransactionForm) {
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		return template.execute(new TransactionCallback<UserDetail>() {

			@Override
			public UserDetail doInTransaction(TransactionStatus status) {

				Connection connection = connectionRepository.findById(financialTransactionForm.getConnectionId());
				FinancialTransaction financialTransaction = financialTransactionRepository.create(userDetail.getUser(),
						connection, financialTransactionForm.getAmount());
				connectionRepository.addFinancialTransaction(connection, financialTransaction);
				
				List<Connection> connections = userDetail.getUser().getConnections();
				financialTransactionRepository.getAllFinancialTransaction(connections);
				return userMapper.map(userDetail.getUser());
			}

		});

	}

	/**
	 * Adds the connection.
	 *
	 * @param userDetail the user detail
	 * @param connectionForm the connection form
	 * @return the user detail
	 */
	@Override
	public UserDetail addConnection(UserDetail userDetail, ConnectionForm connectionForm) {
		logger.info("ajout d'une connection avec l'utilisateur {} pour l'utilisateur", connectionForm.getUserId(), userDetail.getUser().getId());
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		return template.execute(new TransactionCallback<UserDetail>() {

			@Override
			public UserDetail doInTransaction(TransactionStatus status) {

				User user = userDetail.getUser();
				Connection connection = connectionRepository.create(connectionForm.getUserId(),
						connectionForm.getDescription());
				connectionRepository.addToUser(user, connection);
				user.setConnections(connectionRepository.getAllConnection(user));
				financialTransactionRepository.getAllFinancialTransaction(user.getConnections());
				return userMapper.map(user);
			}

		});
	}

}
