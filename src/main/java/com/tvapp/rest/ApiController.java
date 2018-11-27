package com.tvapp.rest;

import com.sun.istack.internal.Nullable;
import com.tvapp.Constants;
import com.tvapp.model.ApiModel;
import com.tvapp.repository.ApiRepository;
import com.tvapp.thetvdb.TheTVDBDAO;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class ApiController {

    private final ApiRepository apiRepository;
    private final TheTVDBDAO theTVDBDAO = new TheTVDBDAO();

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
        @Nullable
        String token = body.get("token");
        @Nullable
        String userName = body.get("user");
        @Nullable
        String password = body.get("password");
        @Nullable
        String apiKey = body.get("api_key");
        @Nullable
        String userKey = body.get("user_key");

        return apiRepository.save(new ApiModel(name, token, userName, password, apiKey, userKey));
    }

    @GetMapping("/refresh")
    public ApiModel refreshToken() {
        ApiModel apiToken = apiRepository.findByName(Constants.THE_TV_DB);
        apiToken.setToken(theTVDBDAO.refreshToken(apiToken).getToken());
        apiToken.setCreationDate(new Date());
        apiRepository.save(apiToken);
        return apiToken;
    }
}
