package com.tipsontech.employeesapp.dtos;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
	private Integer empId;
	private String name;
	private AddressDTO address;
	private Timestamp timestamp;
}