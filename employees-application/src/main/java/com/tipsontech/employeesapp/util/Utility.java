package com.tipsontech.employeesapp.util;

import java.time.Duration;
import java.time.Instant;
import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tipsontech.employeesapp.dtos.AddressDTO;
import com.tipsontech.employeesapp.dtos.EmployeeDTO;
import com.tipsontech.employeesapp.dtos.UserInfoDTO;
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
		AddressDTO addressDTO = AddressDTO.builder().addressId(emp.getAddress().getAddressId())
				.city(emp.getAddress().getCity()).state(emp.getAddress().getState())
				.country(emp.getAddress().getCountry()).zipcode(emp.getAddress().getZipcode()).build();

		return EmployeeDTO.builder().empId(emp.getEmpId()).name(emp.getName()).address(addressDTO)
				.timestamp(emp.getTimestamp()).build();
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

	public static Instant getTime() {
		// Parse time strings to LocalTime objects
//		return LocalTime.now();
		return Instant.now();
	}

	public static String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = (duration.toMinutes() % 60);
		long seconds = (duration.getSeconds() % 60);

		System.out.println("Hours: " + hours);
		System.out.println("Minutes: " + minutes);
		System.out.println("Seconds: " + seconds);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}