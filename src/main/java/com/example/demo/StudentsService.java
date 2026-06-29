package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class StudentsService {

    @Autowired
    StudentsRepository repo;

    public void saveStudents(Students user) {
        repo.save(user);
    }

    public Students login(String username, String password) {

        Students dbUser =
                repo.findByUsernameAndPassword(username, password);

        return dbUser;
    }
    
    public List<Students> getPendingAdminRequests() {
        return repo.findByRole("PENDING_ADMIN");
    }

    public void updateRole(Long id, String role) {

        Students user = repo.findById(id).orElse(null);

        if (user != null) {
            user.setRole(role);
            repo.save(user);
        }
    }
}