package com.tipsontech.employeesapp.service;

import java.util.List;

import com.tipsontech.employeesapp.dtos.EmployeeDTO;
import com.tipsontech.employeesapp.pojos.EmployeePOJO;

public interface IEmployeeService {
	public List<EmployeeDTO> employees();

	public EmployeeDTO save(EmployeePOJO emp);

	public String delete(Integer empId);

	public String deleteAll();

	public EmployeeDTO employee(Integer empId);

	public EmployeeDTO update(EmployeeDTO emp);
}
