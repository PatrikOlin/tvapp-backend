package com.tvapp.rest;

import com.tvapp.model.UserDetails;
import com.tvapp.repository.UserRepository;
import com.tvapp.utils.exceptions.user.InvalidUserException;
import com.tvapp.utils.exceptions.user.UserExistException;
import com.tvapp.utils.services.BCryptService;
import com.tvapp.utils.services.Base64Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {
    
    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * search for a user with email as param
     *
     * @param header contains email and password
     * @return a user
     */
    @GetMapping
    public String userLogin(@RequestHeader Map<String, String> header) {
        String login = header.get("authorization").replace("Basic ", "");
        String[] userKey = Base64Service.decodeLogin(login);
        UserDetails userDetails = userRepository.findByEmail(userKey[0]);

        if (userDetails != null) {
            if (BCryptService.checkPassword(userKey[1], userDetails.getPassword())) {
                return Base64Service.encodeData(Integer.toString(userDetails.getId()));
            } else {
                throw new InvalidUserException();
            }
        } else {
            throw new InvalidUserException();
        }

    }

    /**
     * Creates a new user in database, if
     * user already exist throws a 401 Unauthorized
     *
     * @param header to map header
     * @return the user
     */
    @PostMapping("/login")
    public String createUser(@RequestHeader Map<String, String> header) {
        String email = header.get("email");
        String password = Base64Service.decodeData(header.get("password"));

        UserDetails user = userRepository.findByEmail(email);

        if (user != null) throw new UserExistException();

        Integer id = userRepository.save(new UserDetails(email, password)).getId();
        
        
        return Base64Service.encodeData(id.toString());
    }
}
