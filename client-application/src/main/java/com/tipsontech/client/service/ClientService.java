package com.tipsontech.client.service;

import java.util.List;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.tipsontech.client.response.EmployeeDTO;

@HttpExchange(value = "/api/v1")
public interface ClientService {

	@GetExchange(value = "/employees")
	List<EmployeeDTO> getEmployees();

}
