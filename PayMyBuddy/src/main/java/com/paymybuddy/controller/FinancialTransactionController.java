package com.paymybuddy.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paymybuddy.dto.UserDetail;
import com.paymybuddy.form.FinancialTransactionForm;
import com.paymybuddy.service.UserService;

import lombok.AllArgsConstructor;

/**
 * The Class FinancialTransactionController.
 */
@Controller

/**
 * Instantiates a new financial transaction controller.
 *
 * @param userService the user service
 */
@AllArgsConstructor
public class FinancialTransactionController {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(FinancialTransactionController.class);
	
	/** The user service. */
	UserService userService;

	/**
	 * Creates the financial transaction.
	 *
	 * @param financialTransactionForm the financial transaction form
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/financialTransactions", method = RequestMethod.POST)
	public String createFinancialTransaction(@ModelAttribute FinancialTransactionForm financialTransactionForm,
			HttpSession session) {
		logger.info("creation d'une transaction financiere");
		UserDetail userDetail = (UserDetail) session.getAttribute("userDetail");
		userDetail = userService.addFinancialtransaction(userDetail, financialTransactionForm);
		session.setAttribute("userDetail", userDetail);
		return "redirect:transfer";
	}
}
