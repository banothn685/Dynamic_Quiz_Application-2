package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository  extends JpaRepository<Students,Long>{
	Students findByUsernameAndPassword(String username, String password);
	List<Students> findByRole(String role);
	
}
