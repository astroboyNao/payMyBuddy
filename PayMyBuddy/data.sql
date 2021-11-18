INSERT INTO paymybuddy_db.`user` (email,password,account_id) VALUES
	 ('email1@host.com','password1',1),
	 ('email2@host.com','password2',2),
	 ('email3@host.com','password3',3);
	 
INSERT INTO paymybuddy_db.account (amount) VALUES
	 (30),
	 (40),
	 (38);

INSERT INTO paymybuddy_db.`connection` (description,user_target_id) VALUES
	 ('restaurant',2),
 	 ('cadeaux',2);
 	 
INSERT INTO paymybuddy_db.user_connections (user_id,connections_id) VALUES
	 (1,1),
	 (1,2);
	 
INSERT INTO paymybuddy_db.financial_transaction (amount,account_destination_id,account_source_id) VALUES
	 (10.0,1,NULL),
	 (20.0,1,NULL);
	 
INSERT INTO paymybuddy_db.connection_financial_transactions (connection_id,financial_transactions_id) VALUES
	 (1,1),
	 (2,2);