package com.tvapp;

import com.tvapp.dao.ShowDAO;
import com.tvapp.model.Episode;
import com.tvapp.repository.EpisodeRepository;
import com.tvapp.rest.EpisodeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@SpringBootApplication
public class MainApp extends SpringBootServletInitializer {



    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);

        Date date = new Date();
        ShowDAO show = new ShowDAO();
        show.getShow("walking dead");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApp.class);
    }

}
