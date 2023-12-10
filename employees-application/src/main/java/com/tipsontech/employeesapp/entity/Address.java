package com.tipsontech.employeesapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Integer addressId;

	@Column(name = "city", length = 10)
	private String city;

	@Column(name = "state", length = 10)
	private String state;

	@Column(name = "country", length = 10)
	private String country;

	@Column(name = "zipcode", length = 10)
	private String zipcode;

	@OneToOne(mappedBy = "address")
	private Employee employee;
}
