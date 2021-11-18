package com.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FinancialTransactionDTO {
	private Long id;
	private String userTargetEmail;
	private String description;
	private Float amount;
}
