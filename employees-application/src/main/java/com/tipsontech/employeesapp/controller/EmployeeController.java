package com.tipsontech.employeesapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tipsontech.employeesapp.dtos.EmployeeDTO;
import com.tipsontech.employeesapp.pojos.EmployeePOJO;
import com.tipsontech.employeesapp.service.IEmployeeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/api/v1")
@SecurityRequirement(name = "Authorization")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping(value = "/employees")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<EmployeeDTO> employees() {
		return employeeService.employees();
	}

	@GetMapping(value = "/employees/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public EmployeeDTO employee(@PathVariable(value = "id") Integer empId) {
		return employeeService.employee(empId);
	}

	@PostMapping(value = "/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeDTO save(@RequestBody EmployeePOJO emp) {
		return employeeService.save(emp);
	}

	@DeleteMapping(value = "/employees")
	@ResponseStatus(HttpStatus.OK)
	public String deleteAll() {
		return employeeService.deleteAll();
	}

	@DeleteMapping(value = "/employees/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String delete(@PathVariable(value = "id") Integer empId) {
		return employeeService.delete(empId);
	}

	@PutMapping(value = "/employees")
	@ResponseStatus(HttpStatus.OK)
	public EmployeeDTO update(@RequestBody EmployeeDTO emp) {
		return employeeService.update(emp);
	}

}