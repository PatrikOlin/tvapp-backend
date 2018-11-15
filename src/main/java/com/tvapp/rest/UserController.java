package com.tvapp.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
public class UserController {

    private final UserRepository userRepository;

    private final UserResourceAssembler assembler;

    UserController(UserRepository userRepository, UserResourceAssembler assembler) {
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    @GetMapping("/user")
    Resources<Resource<User>> all() {
        List<Resource<User>> users = userRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/user/{email}")
    Resource<User> one(@PathVariable String email) {

        User user = userRepository.findByEmail(email);

        return assembler.toResource(user);
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
