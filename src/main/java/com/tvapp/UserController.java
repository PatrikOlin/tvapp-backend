package com.tvapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public List<User> index() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User show(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        return userRepository.findOne(userId);
    }

    @PostMapping("/user/search")
    public List<User> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return userRepository.findByEmailContaining(searchTerm);
    }

    @PostMapping("/user")
    public User create(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return userRepository.save(new User(email, password));
    }

    @PutMapping("/user/{id}")
    public User update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(id);
        User user = userRepository.findOne(userId);
        user.setPassword(body.get("password"));
        user.setEmail(body.get("email"));
        return userRepository.save(user);
    }

    @DeleteMapping("/user/{id}")
    public boolean delete(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        userRepository.delete(userId);
        return true;
    }
}
