package com.paymybuddy.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

	private Long id;
	private String email;
	private List<ConnectionDTO> connections;
}
