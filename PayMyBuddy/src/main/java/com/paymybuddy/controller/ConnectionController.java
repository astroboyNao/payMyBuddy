package com.paymybuddy.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.form.ConnectionForm;
import com.paymybuddy.service.UserService;

import lombok.AllArgsConstructor;

/**
 * The Class ConnectionController.
 */
@Controller

/**
 * Instantiates a new connection controller.
 *
 * @param userService the user service
 */
@AllArgsConstructor
public class ConnectionController {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	/** The user service. */
	UserService userService;

	/**
	 * Creates the connection.
	 *
	 * @param connectionForm the connection form
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/connections", method = RequestMethod.POST)
	public String createConnection(@ModelAttribute ConnectionForm connectionForm, HttpSession session) {
		logger.info("creation d'une connection");
		UserDetail userDetail = (UserDetail) session.getAttribute("userDetail");
		userDetail = userService.addConnection(userDetail, connectionForm);
		session.setAttribute("userDetail", userDetail);
		return "redirect:transfer";
	}
}
