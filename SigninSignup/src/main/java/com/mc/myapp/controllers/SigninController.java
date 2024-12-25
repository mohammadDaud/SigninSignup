package com.mc.myapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.myapp.jwt.JwtAuthFilter;
import com.mc.myapp.jwt.TokenGenerator;
import com.mc.myapp.models.JwtRequest;
import com.mc.myapp.models.JwtResponse;

@RestController
@RequestMapping("/api/signin")
public class SigninController {

	@Value("${app.jwtSecret}")
	private char[] secretKey;

	@Value("${salt}")
	private String salt;

	private final TokenGenerator tokenGenerator;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public SigninController(TokenGenerator tokenGenerator, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenGenerator = tokenGenerator;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@PostMapping(value = "/login")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest loginRequest) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenGenerator.generateToken(authentication);
		List<String> roles = authentication.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtAuthFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		String jwttoken =jwt;
		return new ResponseEntity<>(new JwtResponse(jwttoken,loginRequest.getEmail(),roles),httpHeaders, HttpStatus.OK);
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<Boolean> logout() {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok(Boolean.TRUE);
	}
}
