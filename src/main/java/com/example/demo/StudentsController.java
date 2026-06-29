package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentsController {

    @Autowired
    private StudentsService service;

    @GetMapping("/mcqregister")
    public String showRegisterPage() {
        return "mcqregister";
    }
    @GetMapping("/")
    public String index() {
        return "redirect:/mcqlogin";
    }
    @GetMapping("/mcqlogin")
    public String showLoginPage() {
        return "mcqlogin";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null) {
            return "redirect:/mcqlogin";
        }

        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "welcome";
    }

    @PostMapping("/mcqlogin")
    public String login(Students user, HttpSession session) {

        Students dbUser = service.login(
                user.getUsername(),
                user.getPassword()
        );

        if (dbUser != null) {

            session.setAttribute("username", dbUser.getUsername());
            session.setAttribute("role", dbUser.getRole());

            return "redirect:/home";
        }

        return "invalid";
    }

    @PostMapping("/mcqregister")
    public String register(Students user) {

        if ("ADMIN".equals(user.getRole())) {
            user.setRole("PENDING_ADMIN");
        } else {
            user.setRole("USER");
        }

        service.saveStudents(user);

        return "redirect:/mcqlogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/mcqlogin";
    }

    @GetMapping("/view-quiz")
    public String viewQuiz(HttpSession session) {

        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/mcqlogin";
        }

        return "view-quiz";
    }
    
    @GetMapping("/admin-requests")
    public String adminRequests(HttpSession session, Model model) {

        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (!"ADMIN".equals(role) || !"Banoth Naresh".equals(username)) {
            return "redirect:/home";
        }

        model.addAttribute("requests", service.getPendingAdminRequests());

        return "admin-requests";
    }


    @GetMapping("/approve-admin")
    public String approveAdmin(@RequestParam Long id, HttpSession session) {

        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (!"ADMIN".equals(role) || !"Banoth Naresh".equals(username)) {
            return "redirect:/home";
        }

        service.updateRole(id, "ADMIN");

        return "redirect:/admin-requests";
    }


    @GetMapping("/reject-admin")
    public String rejectAdmin(@RequestParam Long id, HttpSession session) {

        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (!"ADMIN".equals(role) || !"Banoth Naresh".equals(username)) {
            return "redirect:/home";
        }

        service.updateRole(id, "USER");

        return "redirect:/admin-requests";
    }
    
    
}