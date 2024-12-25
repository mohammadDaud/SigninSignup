package com.mc.myapp.controllers;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mc.myapp.entities.User;
import com.mc.myapp.models.SignupDto;
import com.mc.myapp.services.UserService;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

	private final Logger log = LoggerFactory.getLogger(SignupController.class);

	private final UserService userService;

	public SignupController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createAccount(@Valid @RequestBody SignupDto signupDto) {
		if (isPasswordLengthInvalid(signupDto.getPassword())) {
			throw new RuntimeException("Invalid Password");
		}
		User user = userService.registerUser(signupDto, signupDto.getPassword());
		log.debug("SignUp User    " + user);
	}

	private static boolean isPasswordLengthInvalid(String password) {
		return (StringUtils.isEmpty(password));
	}
}
