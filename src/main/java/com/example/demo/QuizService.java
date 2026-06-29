package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    public void saveResult(QuizResult quizResult) {
        quizResultRepository.save(quizResult);
    }

    public List<QuizResult> getAllResults() {
        return quizResultRepository.findAll();
    }
}