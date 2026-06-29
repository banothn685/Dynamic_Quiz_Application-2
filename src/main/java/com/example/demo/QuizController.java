package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @GetMapping("/user-quiz")
    public String showUserQuizPage(HttpSession session) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/mcqlogin";
        }

        return "user-quiz";
    }
    
    @GetMapping("/take-quiz")
    public String takeQuiz(@RequestParam String type,
                           HttpSession session,
                           Model model) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/mcqlogin";
        }

        List<Question> questions =
                questionService.getRandomQuestionsByType(type);

        model.addAttribute("questions", questions);
        model.addAttribute("type", type);

        return "take-quiz";
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam String type,
                             @RequestParam List<Long> questionId,
                             HttpServletRequest request) {

        int score = 0;

        for (Long id : questionId) {

            String selectedAnswer = request.getParameter("answer_" + id);

            Question question = questionService.getQuestionById(id);

            if (question != null
                    && question.getCorrectAnswer().equalsIgnoreCase(selectedAnswer)) {
                score++;
            }
        }

        int totalQuestions = questionId.size();

        double percentage = 0;

        if (totalQuestions > 0) {
            percentage = ((double) score / totalQuestions) * 100;
        }

        QuizResult result = new QuizResult();

        result.setUsername("User");
        result.setType(type);
        result.setScore(score);
        result.setTotalQuestions(totalQuestions);
        result.setPercentage(percentage);

        quizService.saveResult(result);

        return "redirect:/result";
    }
    
    @GetMapping("/result")
    public String showQuizResults(Model model) {

        List<QuizResult> results = quizService.getAllResults();

        int totalQuizzesTaken = results.size();

        double overallAverage = 0;
        double javaAverage = 0;
        double pythonAverage = 0;
        double javaScriptAverage = 0;
        double htmlAverage = 0;
        double cssAverage = 0;

        if (totalQuizzesTaken > 0) {

            overallAverage = results.stream()
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);

            javaAverage = results.stream()
                    .filter(r -> r.getType().equalsIgnoreCase("Java"))
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);

            pythonAverage = results.stream()
                    .filter(r -> r.getType().equalsIgnoreCase("Python"))
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);

            javaScriptAverage = results.stream()
                    .filter(r -> r.getType().equalsIgnoreCase("JavaScript"))
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);

            htmlAverage = results.stream()
                    .filter(r -> r.getType().equalsIgnoreCase("HTML"))
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);

            cssAverage = results.stream()
                    .filter(r -> r.getType().equalsIgnoreCase("CSS"))
                    .mapToDouble(QuizResult::getPercentage)
                    .average()
                    .orElse(0);
        }

        model.addAttribute("results", results);
        model.addAttribute("totalQuizzesTaken", totalQuizzesTaken);
        model.addAttribute("overallAverage", overallAverage);
        model.addAttribute("javaAverage", javaAverage);
        model.addAttribute("pythonAverage", pythonAverage);
        model.addAttribute("javaScriptAverage", javaScriptAverage);
        model.addAttribute("htmlAverage", htmlAverage);
        model.addAttribute("cssAverage", cssAverage);

        return "quiz-results";
    }
}