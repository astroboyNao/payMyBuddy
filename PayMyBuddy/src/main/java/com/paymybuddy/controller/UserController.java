package com.paymybuddy.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.repository.entity.User;
import com.paymybuddy.service.UserService;

import lombok.AllArgsConstructor;

/**
 * The Class UserController.
 */
@Controller

/**
 * Instantiates a new user controller.
 *
 * @param userService the user service
 */
@AllArgsConstructor
public class UserController {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/** The user service. */
	private UserService userService;

	/**
	 * Login.
	 *
	 * @param userToSign the user to sign
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String login(@ModelAttribute User userToSign, HttpSession session) {
		logger.info("connection de l'utilisateur {}", userToSign.getEmail());
		UserDetail userDetail = userService.getUser(userToSign.getPassword());
		session.setAttribute("userDetail", userDetail);
		List<User> users = userService.getAll();
		session.setAttribute("users", users);
		return "redirect:transfer";
	}

}
