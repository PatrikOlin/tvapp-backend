package com.tvapp.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDetails, Resource<UserDetails>> {


    @Override
    public Resource<UserDetails> toResource(UserDetails userDetails) {
        return new Resource<>(userDetails,
                linkTo(methodOn(UserController.class).one(userDetails.getEmail())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("userDetails"));
    }
}
