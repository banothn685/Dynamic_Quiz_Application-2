package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question,Long>{
	@Query(value = "SELECT * FROM question WHERE type = ?1 ORDER BY RAND() LIMIT 10",
	           nativeQuery = true)
	    List<Question> getRandomQuestionsByType(String type);

}
