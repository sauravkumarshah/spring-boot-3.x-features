package com.tipsontech.employeesapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tipsontech.employeesapp.dto.JwtRequest;
import com.tipsontech.employeesapp.dto.JwtResponse;
import com.tipsontech.employeesapp.security.JwtService;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "/login")
	@ResponseStatus(HttpStatus.OK)
	public JwtResponse addUser(@RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtService.generateToken(userDetails.getUsername());
		return JwtResponse.builder().username(userDetails.getUsername()).jwtToken(token).build();
	}

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);

		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username and password!!!");
		}
	}

}
