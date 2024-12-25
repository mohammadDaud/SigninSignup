package com.mc.myapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.myapp.models.UserDto;
import com.mc.myapp.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/getUsers")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<UserDto>> getUserProfile() {
		List<UserDto> userlist = userService.getUsers();
		return ResponseEntity.ok(userlist);
	}

}
