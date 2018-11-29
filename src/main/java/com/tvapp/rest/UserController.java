package com.tvapp.rest;


import com.tvapp.model.UserDetails;
import com.tvapp.repository.UserRepository;
import com.tvapp.utils.UserResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rest Controller for user
 * @author Patrik Holmkvist & Patrik Olin
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserResourceAssembler assembler;

    /**
     * Constructor
     * @param userRepository
     * @param assembler
     */
    UserController(UserRepository userRepository, UserResourceAssembler assembler) {
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    /**
     *  Retrieving all users in database
     * @return List of users
     */
    @GetMapping
    public Resources<Resource<UserDetails>> all() {
        List<Resource<UserDetails>> users = userRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    // TODO: user login auth
    /**
     * search for a user with email as param
     * @param email of user
     * @return a user
     */
    @GetMapping("/login/{email}")
    public Resource<UserDetails> one(@PathVariable String email) {

        UserDetails userDetails = userRepository.findByEmail(email);

        return assembler.toResource(userDetails);
    }

    /**
     * Creates a new user in database
     * @param header to map requestBody
     * @return the user
     */
    @PostMapping
    public UserDetails create(@RequestHeader Map<String, String> header) {
        String email = header.get("email");
        String password = header.get("password");

        return userRepository.save(new UserDetails(email, password));
    }

    /**
     * Updates a existing user in database
     * @param id of user
     * @param body is the requestBody
     * @return updated UserDetails
     */
    @PutMapping("/{id}")
    public UserDetails update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(id);
        UserDetails userDetails = userRepository.findOne(userId);
        userDetails.setPassword(body.get("password"));
        userDetails.setEmail(body.get("email"));
        return userRepository.save(userDetails);
    }

    /**
     *  Delete an exiating user in th database
     * @param id of user
     * @return a boolean value
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int userId = Integer.parseInt(id);
        userRepository.delete(userId);
        return true;
    }



}
