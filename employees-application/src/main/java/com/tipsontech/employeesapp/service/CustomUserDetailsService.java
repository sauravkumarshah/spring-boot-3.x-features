package com.tipsontech.employeesapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tipsontech.employeesapp.config.UserInfoUserDetails;
import com.tipsontech.employeesapp.dto.UserInfoDTO;
import com.tipsontech.employeesapp.entity.UserInfo;
import com.tipsontech.employeesapp.repository.IUserInfoRepository;
import com.tipsontech.employeesapp.request.UserInfoRequest;
import com.tipsontech.employeesapp.util.Utility;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserInfoRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ObservationRegistry registry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = repository.findByEmail(username);

		return userInfo.map(UserInfoUserDetails::new)
				.orElseThrow(() -> Utility.usernameNotFoundException("Given user not found : " + username));
	}

	public UserInfoDTO addUser(UserInfoRequest userInfoRequest) {
		userInfoRequest.setPassword(encoder.encode(userInfoRequest.getPassword()));

		UserInfo userInfo = UserInfo.builder().name(userInfoRequest.getName()).email(userInfoRequest.getEmail())
				.password(userInfoRequest.getPassword()).roles(userInfoRequest.getRoles()).build();

		return Observation.createNotStarted("addUser", registry)
				.observe(() -> Utility.mapToUserInfoDTO(repository.save(userInfo)));
	}

	public List<UserInfoDTO> users() {
		return Observation.createNotStarted("getUsers", registry)
				.observe(() -> repository.findAll().stream().map(Utility::mapToUserInfoDTO).toList());
	}

}
