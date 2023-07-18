package com.tipsontech.employeesapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tipsontech.employeesapp.dto.UserInfoDTO;
import com.tipsontech.employeesapp.request.UserInfoRequest;
import com.tipsontech.employeesapp.service.CustomUserDetailsService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping(value = "/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserInfoDTO addUser(@RequestBody UserInfoRequest userInfo) {
		return customUserDetailsService.addUser(userInfo);
	}

	@GetMapping(value = "/user")
	@ResponseStatus(HttpStatus.OK)
	@Timed(value = "fetching.users.time", description = "Time taken to return list of users", percentiles = { 0.5,
			0.90 })
	public List<UserInfoDTO> users() {
		return customUserDetailsService.users();
	}

}
