package com.tvapp.rest;

import com.tvapp.model.UserDetails;
import com.tvapp.repository.UserRepository;
import com.tvapp.utils.UserResourceAssembler;
import com.tvapp.utils.constants.ReqConst;
import com.tvapp.utils.services.Base64Service;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository
     */
    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates a existing user in database
     *
     * @param header to map id and password
     * @return updated UserDetails
     */
    @PutMapping
    public ResponseEntity updateUser(@RequestHeader Map<String, String> header) {
        String userInfo = header.get(ReqConst.USERDATA);
        String[] userData = Base64Service.decodeLogin(userInfo);
        UserDetails userDetails = userRepository.findOne(Integer.parseInt(userData[0]));
        userDetails.setPassword(userData[1]);
        userRepository.save(userDetails);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Delete an exiating user in th database
     *
     * @param header
     * @return a boolean value
     */
    @DeleteMapping
    public boolean delete(@RequestHeader Map<String, String> header) {
        int userId = Integer.parseInt(Base64Service.decodeData(header.get(ReqConst.USERID)));
        userRepository.delete(userId);
        return true;
    }


}
