package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionservice;

    @GetMapping("/create-question")
    public String showCreateQuestionPage(HttpSession session) {

        String username = (String) session.getAttribute("username");
        
        System.out.println("Username in session = " + username);
        
        if (username == null) {
            return "redirect:/mcqlogin";
        }

        return "create-question";
    }

    @PostMapping("/save-question")
    public String saveQuestion(Question question) {

        questionservice.saveQuestion(question);

        return "redirect:/create-question?success=true";
    }

    @GetMapping("/all-questions")
    public String showAllQuestion(Model model) {

        List<Question> questions = questionservice.getAllQuestions();

        model.addAttribute("questions", questions);

        return "all-questions";
    }
    
}