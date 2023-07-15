package com.tipsontech.employeesapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipsontech.employeesapp.entity.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}