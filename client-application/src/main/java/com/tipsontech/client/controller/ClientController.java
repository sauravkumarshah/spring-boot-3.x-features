package com.tipsontech.client.controller;

import java.util.List;

import com.tipsontech.client.response.EmployeeDTO;
import com.tipsontech.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping(value = "/employees")
	public List<EmployeeDTO> getEmployees() {
		return clientService.getEmployees();
	}
}
