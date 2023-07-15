package com.tipsontech.employeesapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipsontech.employeesapp.dto.EmployeeDTO;
import com.tipsontech.employeesapp.entity.Employee;
import com.tipsontech.employeesapp.repository.IEmployeeRepository;
import com.tipsontech.employeesapp.util.Utility;

@Service
public class EmployeeService {

	@Autowired
	private IEmployeeRepository repository;

	public List<EmployeeDTO> employees() {
		return repository.findAll().stream().map(Utility::mapToEmployeeDTO).toList();
	}

	public EmployeeDTO save(EmployeeDTO emp) {
		Employee employee = Employee.builder().name(emp.getName()).address(emp.getAddress()).build();
		return Utility.mapToEmployeeDTO(repository.save(employee));
	}

	public String delete(Integer empId) {
		Employee employee = repository.findById(empId).orElseThrow(() -> Utility.notFound(empId));
		repository.delete(employee);
		return "Employee with id=" + empId + " removed";
	}

	public String deleteAll() {
		List<Employee> employees = repository.findAll();
		if (employees.isEmpty())
			return "No employees available";
		repository.deleteAll();
		return "All employees are removed.";
	}

	public EmployeeDTO employee(Integer empId) {
		Employee employee = repository.findById(empId).orElseThrow(() -> Utility.notFound(empId));
		return Utility.mapToEmployeeDTO(employee);
	}

	public EmployeeDTO update(EmployeeDTO emp) {
		repository.findById(emp.getId()).orElseThrow(() -> Utility.notFound(emp.getId()));
		Employee employee = Employee.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
		return Utility.mapToEmployeeDTO(repository.save(employee));
	}
}