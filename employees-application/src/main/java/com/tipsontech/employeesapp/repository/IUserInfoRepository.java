package com.tipsontech.employeesapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipsontech.employeesapp.entity.UserInfo;

@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfo, Integer> {

	Optional<UserInfo> findByEmail(String username);

}
