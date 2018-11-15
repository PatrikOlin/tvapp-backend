package com.tvapp.rest;

import com.tvapp.model.Show;
import com.tvapp.repository.ShowRepository;
import com.tvapp.utils.ShowResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/shows")
public class ShowController {

  private final ShowRepository showRepository;
  private final ShowResourceAssembler assembler;

  ShowController(ShowRepository showRepository, ShowResourceAssembler assembler) {
      this.showRepository = showRepository;
      this.assembler = assembler;
  }

    @GetMapping
    public Resources<Resource<Show>> all() {
      List<Resource<Show>> shows = showRepository.findAll().stream()
              .map(assembler::toResource)
              .collect(Collectors.toList());

      return new Resources<>(shows,
              linkTo(methodOn(ShowController.class).all()).withSelfRel());
    }

    @GetMapping("/{name}")
    public Resource<Show> one(@PathVariable String name) {

      Show show = showRepository.findByName(name);

      return assembler.toResource(show);
    }

    @PostMapping("/search")
    public List<Show> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("searchQuery");
        return showRepository.findByNameContaining(searchTerm);
    }

    @PostMapping
    public Show create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return showRepository.save(new Show(name));
    }

    @PutMapping("/{id}")
    public Show update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int seriesId = Integer.parseInt(id);
        Show show = showRepository.findOne(seriesId);
        show.setName(body.get("name"));
        return showRepository.save(show);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        showRepository.delete(seriesId);
        return true;
    }
}
