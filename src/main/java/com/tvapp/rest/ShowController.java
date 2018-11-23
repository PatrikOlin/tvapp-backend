package com.tvapp.rest;

import com.tvapp.dao.MovieDBDAO;
import com.tvapp.dao.TheTVDBDAO;
import com.tvapp.themoviedb.Result;
import com.tvapp.model.Show;
import com.tvapp.repository.ShowRepository;
import com.tvapp.themoviedb.Season;
import com.tvapp.themoviedb.ShowDetails;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/shows")
public class ShowController {

  private final ShowRepository showRepository;
  private final ShowResourceAssembler assembler;
  private MovieDBDAO movieDBDAO = new MovieDBDAO();

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

      Show show = showRepository.findByTitle(name);

      return assembler.toResource(show);
    }

    @GetMapping("/search")
    public List<Result> search(@RequestParam Map<String, String> param) {
        String searchTerm = param.get("searchQuery");
        //return showRepository.findByNameContaining(searchTerm);
        return movieDBDAO.searchShows(searchTerm);
    }

    @GetMapping("/details")
    public ShowDetails getShow(@RequestParam Map<String, String> param) {
      String id = param.get("show_id");
        return movieDBDAO.ShowDetails(id);
    }

    @GetMapping("/details/season")
    public Season getSeason(@RequestParam Map<String, String> param) {
        String id = param.get("show_id");
        String season = param.get("season");
        return movieDBDAO.ShowSeason(id, season);
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
        show.setTitle(body.get("name"));
        return showRepository.save(show);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        showRepository.delete(seriesId);
        return true;
    }
}
