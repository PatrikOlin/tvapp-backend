package com.tvapp.rest;

import com.tvapp.model.ApiModel;
import com.tvapp.repository.ApiRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class ApiController {

    private final ApiRepository apiRepository;

    public ApiController(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping
    public List<ApiModel> getAll() {
        return apiRepository.findAll();
    }

    @PostMapping
    public ApiModel create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String token = body.get("token");
        return apiRepository.save(new ApiModel(name, token));
    }
}
