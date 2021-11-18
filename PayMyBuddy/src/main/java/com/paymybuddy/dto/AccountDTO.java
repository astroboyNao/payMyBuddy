package com.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
	private Long id;
	private UserDTO user;
	private Long amount;
}
