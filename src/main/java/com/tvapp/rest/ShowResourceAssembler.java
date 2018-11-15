package com.tvapp.rest;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class ShowResourceAssembler implements ResourceAssembler<Show, Resource<Show>> {


    @Override
    public Resource<Show> toResource(Show show) {
        return new Resource<Show>(show,
                linkTo(methodOn(ShowController.class).one(show.getName())).withSelfRel(),
                linkTo(methodOn(ShowController.class).all()).withRel("shows"));
    }
}
