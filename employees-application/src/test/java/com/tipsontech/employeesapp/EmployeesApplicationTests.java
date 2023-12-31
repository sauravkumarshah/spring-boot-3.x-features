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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tipsontech.employeesapp.entity.Address;
import com.tipsontech.employeesapp.entity.Employee;
import com.tipsontech.employeesapp.pojos.AddressPOJO;
import com.tipsontech.employeesapp.pojos.EmployeePOJO;
import com.tipsontech.employeesapp.repository.IEmployeeRepository;
import com.tipsontech.employeesapp.repository.IUserInfoRepository;
import com.tipsontech.employeesapp.request.UserInfoRequest;

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
	private IUserInfoRepository userInfoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private final static List<EmployeePOJO> employees = new ArrayList<>();

	private final static List<UserInfoRequest> users = new ArrayList<>();

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

		AddressPOJO address = AddressPOJO.builder().city("city1").state("state1").country("country1").build();

		EmployeePOJO emp1 = EmployeePOJO.builder().name("test emp1").address(address).build();
		EmployeePOJO emp2 = EmployeePOJO.builder().name("test emp2").address(address).build();
		EmployeePOJO emp3 = EmployeePOJO.builder().name("test emp3").address(address).build();
		EmployeePOJO emp4 = EmployeePOJO.builder().name("test emp4").address(address).build();
		EmployeePOJO emp5 = EmployeePOJO.builder().name("test emp5").address(address).build();

		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		employees.add(emp5);

		UserInfoRequest admin = UserInfoRequest.builder().name("admin").email("admin@gmail.com").password("password")
				.roles("ROLE_ADMIN").build();
		UserInfoRequest user = UserInfoRequest.builder().name("user").email("user@gmail.com").password("password")
				.roles("ROLE_USER").build();
		UserInfoRequest adminUser = UserInfoRequest.builder().name("adminuser").email("adminuser@gmail.com")
				.password("password").roles("ROLE_ADMIN,ROLE_USER").build();

		users.add(admin);
		users.add(user);
		users.add(adminUser);
	}

	@Test
	@Order(value = 1)
	void testConnectionToDatabase() {
		Assertions.assertNotNull(employeeRepository);
		Assertions.assertNotNull(userInfoRepository);
	}

	@Test
	@Order(value = 2)
	@WithMockUser(username = "admin@gmail.com", roles = { "USER", "ADMIN" })
	void testAddEmployees() throws Exception {
		for (EmployeePOJO employee : employees) {
			String emp = objectMapper.writeValueAsString(employee);
			mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
					.content(emp)).andExpect(status().isCreated());
		}
		Assertions.assertEquals(5, employeeRepository.findAll().size());
	}

	@Test
	@Order(value = 3)
	@WithMockUser(username = "admin@gmail.com", roles = { "ADMIN" })
	void testGetAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")).andExpect(status().isOk());
		Assertions.assertEquals(employees.get(3).getName(), employeeRepository.findById(4).get().getName());
	}

	@Test
	@Order(value = 4)
	@WithMockUser(username = "user@gmail.com", roles = { "USER" })
	void testGetAllEmployeesFailed() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")).andExpect(status().isForbidden());
	}

	@Test
	@Order(value = 5)
	@WithMockUser(username = "user@gmail.com", roles = { "USER" })
	void testGetEmployeeById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/2")).andExpect(status().isOk());
		Assertions.assertEquals(employees.get(1).getName(), employeeRepository.findById(2).get().getName());
	}

	@Test
	@Order(value = 6)
	@WithMockUser(username = "admin@gmail.com", roles = { "ADMIN" })
	void testGetEmployeeByIdFailed() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/2")).andExpect(status().isForbidden());
		Assertions.assertEquals(employees.get(1).getName(), employeeRepository.findById(2).get().getName());
	}

	@Test
	@Order(value = 7)
	@WithMockUser(username = "admin@gmail.com", roles = { "USER", "ADMIN" })
	void testDeleteEmployeeById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/2")).andExpect(status().isOk());
	}

	@Test
	@Order(value = 8)
	@WithMockUser(username = "admin@gmail.com", roles = { "USER", "ADMIN" })
	void testUpdateEmployee() throws Exception {
		Address address = Address.builder().city("Ranchi").state("Jharkhand").country("India East").build();
		Employee employee = Employee.builder().empId(3).name("Saurav Kumar").address(address).build();
		String emp = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/api/v1/employees").contentType(MediaType.APPLICATION_JSON).content(emp))
				.andExpect(status().isOk());
		Assertions.assertEquals(employee.getName(), employeeRepository.findById(3).get().getName());
	}

	@Test
	@Order(value = 9)
	@WithMockUser(username = "admin@gmail.com", roles = { "USER", "ADMIN" })
	void testDeleteAllEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees")).andExpect(status().isOk());
		Assertions.assertEquals(0, employeeRepository.findAll().size());
	}

	@Test
	void testAddUsers() throws Exception {
		for (UserInfoRequest user : users) {
			String usr = objectMapper.writeValueAsString(user);
			mockMvc.perform(
					MockMvcRequestBuilders.post("/api/v1/user").contentType(MediaType.APPLICATION_JSON).content(usr))
					.andExpect(status().isCreated());
		}
		Assertions.assertEquals(3, userInfoRepository.findAll().size());
	}

	@Test
	void testGetUserByUsername() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user")).andExpect(status().isOk());
		Assertions.assertEquals(users.get(1).getName(),
				userInfoRepository.findByEmail("user@gmail.com").get().getName());
	}
}
