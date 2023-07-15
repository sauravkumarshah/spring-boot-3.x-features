package com.tipsontech.employeesapp.util;

import java.util.NoSuchElementException;

import com.tipsontech.employeesapp.dto.EmployeeDTO;
import com.tipsontech.employeesapp.entity.Employee;

public class Utility {
	private Utility() {

	}

	public static NoSuchElementException notFound(Integer empId) {
		return new NoSuchElementException("Employee with id=" + empId + " not found.");
	}

	public static EmployeeDTO mapToEmployeeDTO(Employee emp) {
		return EmployeeDTO.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
	}
}