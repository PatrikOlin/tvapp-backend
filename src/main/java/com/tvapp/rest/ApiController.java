package com.tvapp.rest;

import com.tvapp.utils.constants.UrlConstants;
import com.tvapp.model.Token;
import com.tvapp.repository.ApiRepository;
import com.tvapp.thetvdb.TheTVDBDAO;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/token")
public class ApiController {

    private final ApiRepository apiRepository;
    private final TheTVDBDAO theTVDBDAO = new TheTVDBDAO();

    public ApiController(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    /**
     * Returns all token in database
     *
     * @return list of tokens
     */
    @GetMapping
    public List<Token> getAll() {
        return apiRepository.findAll();
    }

    /**
     * Creates a new token and save it in database
     *
     * @param body to map resources
     * @return a token
     */
    @PostMapping
    public Token create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String token = body.get("token");
        String userName = body.get("user");
        String password = body.get("password");
        String apiKey = body.get("api_key");
        String userKey = body.get("user_key");

        return apiRepository.save(new Token(name, token, userName, password, apiKey, userKey));
    }

    /**
     * For refreshing token if its old
     *
     * @return a token
     */
    @GetMapping("/refresh")
    public Token refreshToken() {
        Token apiToken = apiRepository.findByName(UrlConstants.THE_TV_DB);
        apiToken.setToken(theTVDBDAO.refreshToken(apiToken).getToken());
        apiToken.setCreationDate(new Date());
        apiRepository.save(apiToken);
        return apiToken;
    }
}
