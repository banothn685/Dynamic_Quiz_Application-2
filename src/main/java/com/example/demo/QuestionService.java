package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
    
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    public List<Question> getRandomQuestionsByType(String type) {
        return questionRepository.getRandomQuestionsByType(type);
    }
    
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
    
}