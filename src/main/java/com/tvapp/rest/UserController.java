package com.tvapp.rest;


import com.tvapp.model.UserDetails;
import com.tvapp.repository.UserRepository;
import com.tvapp.utils.exceptions.user.InvalidUserException;
import com.tvapp.utils.exceptions.user.UserExistException;
import com.tvapp.utils.exceptions.user.UserNotFoundException;
import com.tvapp.utils.UserResourceAssembler;
import com.tvapp.utils.services.BCryptService;
import com.tvapp.utils.services.Base64Service;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Rest Controller for user
 *
 * @author Patrik Holmkvist & Patrik Olin
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserResourceAssembler assembler;

    /**
     * Constructor
     *
     * @param userRepository
     * @param assembler
     */
    UserController(UserRepository userRepository, UserResourceAssembler assembler) {
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    // TODO: Säkra/osäkra endpoints
    // TODO: Inte nödvändig
    /**
     * Retrieving all users in database
     *
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

    // TODO: behövs ej eller ändra nästa metod så den returnerar resource??
    /**
     * search for a user with email as param
     *
     * @param email contains email
     * @return a user
     */
    @GetMapping("/login/{email}")
    public Resource<UserDetails> one(@PathVariable String email) {

        UserDetails userDetails = userRepository.findByEmail(email);

        return assembler.toResource(userDetails);
    }

    /**
     * search for a user with email as param
     *
     * @param header contains email and password
     * @return a user
     */
    @GetMapping("/login")
    public int userLogin(@RequestHeader Map<String, String> header) {
        String login = header.get("authorization").replace("Basic ", "");
        String[] userKey = Base64Service.decodeLogin(login);
        UserDetails userDetails = userRepository.findByEmail(userKey[0]);

        if (userDetails != null) {
            if (BCryptService.checkPassword(userKey[1], userDetails.getPassword())) {
                return userDetails.getId();
            } else {
                throw new InvalidUserException();
            }
        } else {
            throw new UserNotFoundException(header.get("email"));
        }

    }

    // TODO: encode id som skickas tillbaka
    /**
     * Creates a new user in database, if
     * user already exist throws a 401 Unauthorized
     *
     * @param header to map header
     * @return the user
     */
    @PostMapping("/login")
    public UserDetails createUser(@RequestHeader Map<String, String> header) {
        String email = header.get("email");
        String password = Base64Service.decodePassword(header.get("password"));

        UserDetails user = userRepository.findByEmail(email);

        if (user != null) throw new UserExistException();

        return userRepository.save(new UserDetails(email, password));
    }

    // TODO: base64encode id and psw in headern returnera 200
    /**
     * Updates a existing user in database
     *
     * @param id   of user
     * @param body is the requestBody
     * @return updated UserDetails
     */
    @PutMapping("/{id}")
    public UserDetails updateUser(@PathVariable String id, @RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(id);
        UserDetails userDetails = userRepository.findOne(userId);
        userDetails.setPassword(body.get("password"));
        return userRepository.save(userDetails);
    }

    // TODO: Kanske inte behövs i våran version. kan vara något som kommer senare...
    /**
     * Delete an exiating user in th database
     *
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
