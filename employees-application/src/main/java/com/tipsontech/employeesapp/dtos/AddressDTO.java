package com.tipsontech.employeesapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	private Integer addressId;
	private String city;
	private String state;
	private String country;
	private String zipcode;
}
