package com.tipsontech.employeesapp.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipsontech.employeesapp.dtos.EmployeeDTO;
import com.tipsontech.employeesapp.entity.Address;
import com.tipsontech.employeesapp.entity.Employee;
import com.tipsontech.employeesapp.pojos.EmployeePOJO;
import com.tipsontech.employeesapp.repository.IEmployeeRepository;
import com.tipsontech.employeesapp.util.Utility;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService implements IEmployeeService{

	@Autowired
	private IEmployeeRepository repository;

	@Autowired
	private ObservationRegistry registry;

	public List<EmployeeDTO> employees() {
		log.info("Fetch all employees");

		return Observation.createNotStarted("getEmployees", registry)
				.observe(() -> repository.findAll().stream().map(Utility::mapToEmployeeDTO)).toList();
	}

	public EmployeeDTO save(EmployeePOJO emp) {
		log.info("Save employee data : {}", emp);

		Address address = Address.builder().city(emp.getAddress().getCity()).state(emp.getAddress().getState())
				.country(emp.getAddress().getCountry()).zipcode(emp.getAddress().getZipcode()).build();

		Employee employee = Employee.builder().name(emp.getName()).address(address)
				.timestamp(new Timestamp(System.currentTimeMillis())).build();

		return Observation.createNotStarted("saveEmployee", registry)
				.observe(() -> Utility.mapToEmployeeDTO(repository.save(employee)));
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

		return Observation.createNotStarted("getEmployeeById", registry)
				.observe(() -> Utility.mapToEmployeeDTO(employee));
	}

	public EmployeeDTO update(EmployeeDTO emp) {
		log.info("Update records of employee having emp id = {}", emp.getEmpId());
		repository.findById(emp.getEmpId()).orElseThrow(() -> Utility.notFound(emp.getEmpId()));

		Address address = Address.builder().city(emp.getAddress().getCity()).state(emp.getAddress().getState())
				.country(emp.getAddress().getCountry()).zipcode(emp.getAddress().getZipcode()).build();

		Employee employee = Employee.builder().empId(emp.getEmpId()).name(emp.getName()).address(address)
				.timestamp(new Timestamp(System.currentTimeMillis())).build();

		return Observation.createNotStarted("updateEmployee", registry)
				.observe(() -> Utility.mapToEmployeeDTO(repository.save(employee)));

	}
}