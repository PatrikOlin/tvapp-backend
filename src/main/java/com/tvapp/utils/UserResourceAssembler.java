package com.tvapp.utils;

import com.tvapp.model.UserDetails;
import com.tvapp.rest.UserController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Component
public class UserResourceAssembler {//implements ResourceAssembler<UserDetails, Resource<UserDetails>> {


//    @Override
//    public Resource<UserDetails> toResource(UserDetails userDetails) {
//        return new Resource<>(userDetails,
//                ControllerLinkBuilder.linkTo(methodOn(UserController.class).one(userDetails.getEmail())).withSelfRel(),
//                linkTo(methodOn(UserController.class).all()).withRel("userDetails"));
//    }
}
