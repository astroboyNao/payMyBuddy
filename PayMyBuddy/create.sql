-- paymybuddy_db.account definition

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- paymybuddy_db.financial_transaction definition

CREATE TABLE `financial_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT NULL,
  `account_destination_id` bigint DEFAULT NULL,
  `account_source_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2t3o9qvpje3j2jyq2iokhifkx` (`account_destination_id`),
  KEY `FKh6358mnq5c5t21w12q0f48ydx` (`account_source_id`),
  CONSTRAINT `FK2t3o9qvpje3j2jyq2iokhifkx` FOREIGN KEY (`account_destination_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKh6358mnq5c5t21w12q0f48ydx` FOREIGN KEY (`account_source_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- paymybuddy_db.`user` definition

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc3b4xfbq6rbkkrddsdum8t5f0` (`account_id`),
  CONSTRAINT `FKc3b4xfbq6rbkkrddsdum8t5f0` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- paymybuddy_db.`connection` definition

CREATE TABLE `connection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `user_target_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5o2rr1gnycyx8n8hin5q7b1wx` (`user_target_id`),
  CONSTRAINT `FK5o2rr1gnycyx8n8hin5q7b1wx` FOREIGN KEY (`user_target_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- paymybuddy_db.connection_financial_transactions definition

CREATE TABLE `connection_financial_transactions` (
  `connection_id` bigint NOT NULL,
  `financial_transactions_id` bigint NOT NULL,
  UNIQUE KEY `UK_hdc77tdrj2g6ulem86mh39rfg` (`financial_transactions_id`),
  KEY `FKt0si8iwq1c8mjpylaroyre737` (`connection_id`),
  CONSTRAINT `FKofufonf7aepgp863c76hregbd` FOREIGN KEY (`financial_transactions_id`) REFERENCES `financial_transaction` (`id`),
  CONSTRAINT `FKt0si8iwq1c8mjpylaroyre737` FOREIGN KEY (`connection_id`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- paymybuddy_db.user_connections definition

CREATE TABLE `user_connections` (
  `user_id` bigint NOT NULL,
  `connections_id` bigint NOT NULL,
  UNIQUE KEY `UK_phslucr0bifoa54t6o4a6dpix` (`connections_id`),
  KEY `FK3yvohvstyav0ifob852rlnecu` (`user_id`),
  CONSTRAINT `FK3yvohvstyav0ifob852rlnecu` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKci7qwa21vq1v9rkcbxsupyiqe` FOREIGN KEY (`connections_id`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;