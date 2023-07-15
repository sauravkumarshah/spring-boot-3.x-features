package com.tipsontech.employeesapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipsontech.employeesapp.dto.EmployeeDTO;
import com.tipsontech.employeesapp.entity.Employee;
import com.tipsontech.employeesapp.repository.IEmployeeRepository;
import com.tipsontech.employeesapp.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private IEmployeeRepository repository;

	public List<EmployeeDTO> employees() {
		log.info("Fetch all employees");

		return repository.findAll().stream().map(Utility::mapToEmployeeDTO).toList();
	}

	public EmployeeDTO save(EmployeeDTO emp) {
		log.info("Save employee data : {}", emp);

		Employee employee = Employee.builder().name(emp.getName()).address(emp.getAddress()).build();

		return Utility.mapToEmployeeDTO(repository.save(employee));
	}

	public String delete(Integer empId) {
		log.info("Delete employee by employee Id");
		Employee employee = repository.findById(empId).orElseThrow(() -> Utility.notFound(empId));
		repository.delete(employee);
		log.info("Employee with id = {} removed", empId);
		return "Employee with id=" + empId + " removed";
	}

	public String deleteAll() {
		log.info("Remove all employees from the database");
		List<Employee> employees = repository.findAll();
		if (employees.isEmpty()) {
			log.info("No employees available in the database");
			return "No employees available";
		}
		repository.deleteAll();
		log.info("All employees are removed from the database");
		return "All employees are removed.";
	}

	public EmployeeDTO employee(Integer empId) {
		log.info("Fetch employee details by emp id = {}", empId);
		Employee employee = repository.findById(empId).orElseThrow(() -> Utility.notFound(empId));
		return Utility.mapToEmployeeDTO(employee);
	}

	public EmployeeDTO update(EmployeeDTO emp) {
		log.info("Update records of employee having emp id = {}", emp.getId());
		repository.findById(emp.getId()).orElseThrow(() -> Utility.notFound(emp.getId()));
		Employee employee = Employee.builder().id(emp.getId()).name(emp.getName()).address(emp.getAddress()).build();
		return Utility.mapToEmployeeDTO(repository.save(employee));
	}
}