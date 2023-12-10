package com.tipsontech.employeesapp.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressPOJO {
	private String city;
	private String state;
	private String country;
	private String zipcode;
}
