package com.paymybuddy.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDetail {
	private com.paymybuddy.repository.entity.User user;
	private List<ConnectionDTO> connections;
	private List<FinancialTransactionDTO> financialTransactions;
}
