package com.tipsontech.employeesapp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tipsontech.employeesapp.dto.EmployeeDTO;
import com.tipsontech.employeesapp.repository.IEmployeeRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class EmployeesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private final static List<EmployeeDTO> employees = new ArrayList<>();

	@Container
	private static final MSSQLServerContainer<?> SQLSERVER_CONTAINER = new MSSQLServerContainer<>(
			"mcr.microsoft.com/mssql/server:2022-latest").acceptLicense();

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", SQLSERVER_CONTAINER::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", SQLSERVER_CONTAINER::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", SQLSERVER_CONTAINER::getPassword);
		dynamicPropertyRegistry.add("spring.jpa.generate-ddl", () -> "true");
		dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
	}

	static {
		SQLSERVER_CONTAINER.start();
	}

	static {

		EmployeeDTO emp1 = EmployeeDTO.builder().name("test emp1").address("address1").build();
		EmployeeDTO emp2 = EmployeeDTO.builder().name("test emp2").address("address2").build();
		EmployeeDTO emp3 = EmployeeDTO.builder().name("test emp3").address("address3").build();
		EmployeeDTO emp4 = EmployeeDTO.builder().name("test emp4").address("address4").build();
		EmployeeDTO emp5 = EmployeeDTO.builder().name("test emp5").address("address5").build();

		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		employees.add(emp5);
	}

	@Test
	@Order(value = 1)
	void testConnectionToDatabase() {
		Assertions.assertNotNull(employeeRepository);
	}

	@Test
	@Order(value = 2)
	void testAddEmployees() throws Exception {
		for (EmployeeDTO employee : employees) {
			String emp = objectMapper.writeValueAsString(employee);
			mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
					.content(emp)).andExpect(status().isCreated());
		}
		Assertions.assertEquals(5, employeeRepository.findAll().size());
	}

	@Test
	@Order(value = 3)
	void testGetAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")).andExpect(status().isOk());
		Assertions.assertEquals(employees.get(3).getName(), employeeRepository.findById(4).get().getName());
	}
}
