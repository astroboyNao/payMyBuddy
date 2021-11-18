package com.paymybuddy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.repository.entity.User;

/**
 * The Class MainController.
 */
@Controller
public class MainController {

	/**
	 * Connection modal.
	 *
	 * @return the string
	 */
	@GetMapping("connectionModal")
	public String connectionModal() {
		return "connectionModal";
	}

	/**
	 * Home.
	 *
	 * @return the string
	 */
	@GetMapping("/home")
	public String home() {
		return "/home";
	}

	/**
	 * Transfer.
	 *
	 * @param session the session
	 * @return the string
	 */
	@GetMapping("/transfer")
	public String transfer(HttpSession session) {
		return "/transfer";
	}

	/**
	 * Profil.
	 *
	 * @return the string
	 */
	@GetMapping("/profil")
	public String profil() {
		return "/profil";
	}

	/**
	 * Contact.
	 *
	 * @return the string
	 */
	@GetMapping("/contact")
	public String contact() {
		return "/contact";
	}

	/**
	 * Login.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("userToCreate", User.builder().build());
		return "/login";
	}

	/**
	 * Login error.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "/login";
	}

	/**
	 * Logout.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("userToCreate", User.builder().build());
		return "/login";
	}
}
