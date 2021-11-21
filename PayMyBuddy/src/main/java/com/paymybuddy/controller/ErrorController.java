package com.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Class ErrorController.
 */
@ControllerAdvice
public class ErrorController {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

	/**
	 * Exception.
	 *
	 * @param throwable the throwable
	 * @param model the model
	 * @return the string
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView exception(final Throwable throwable, final Model model) {
		logger.error("Exception during execution of SpringSecurity application", throwable);
		String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", errorMessage);
		mav.setViewName("error");
		return mav;
	}

}
