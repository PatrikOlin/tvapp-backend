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
    Resources<Resource<UserDetails>> all() {
        List<Resource<UserDetails>> users = userRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/user/{email}")
    Resource<UserDetails> one(@PathVariable String email) {

        UserDetails userDetails = userRepository.findByEmail(email);

        return assembler.toResource(userDetails);
    }

    @PostMapping("/user")
    public UserDetails create(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return userRepository.save(new UserDetails(email, password));
    }

    @PutMapping("/user/{id}")
    public UserDetails update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(id);
        UserDetails userDetails = userRepository.findOne(userId);
        userDetails.setPassword(body.get("password"));
        userDetails.setEmail(body.get("email"));
        return userRepository.save(userDetails);
    }

    @DeleteMapping("/user/{id}")
    public boolean delete(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        userRepository.delete(userId);
        return true;
    }
}
