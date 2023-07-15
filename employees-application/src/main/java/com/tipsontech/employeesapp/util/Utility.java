package com.tipsontech.employeesapp.util;

import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tipsontech.employeesapp.dto.EmployeeDTO;
import com.tipsontech.employeesapp.dto.UserInfoDTO;
import com.tipsontech.employeesapp.entity.Employee;
import com.tipsontech.employeesapp.entity.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utility {
	private Utility() {

	}

	public static NoSuchElementException notFound(Integer empId) {
		log.error("Employee with id = {} not found", empId);
		return new NoSuchElementException("Employee with id = " + empId + " not found.");
	}

	public static EmployeeDTO mapToEmployeeDTO(Employee emp) {
		return EmployeeDTO.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
	}

	public static UserInfoDTO mapToUserInfoDTO(UserInfo userInfo) {
		UserInfoDTO userInfoDTO = UserInfoDTO.builder().id(userInfo.getId()).name(userInfo.getName())
				.email(userInfo.getEmail()).roles(userInfo.getRoles()).build();
		log.info("User details : {}", userInfoDTO);
		return userInfoDTO;
	}

	public static UsernameNotFoundException usernameNotFoundException(String msg) {
		log.error("{}", msg);
		return new UsernameNotFoundException(msg);
	}
}