package com.example.reactivespring.synchronous;

import com.example.reactivespring.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by alexandr.efimov@sigma.software on 10/4/2017.
 */
@RestController
public class MovieControllerSync {

    @Autowired
    private MovieServiceSync movieServiceSync;

    @GetMapping("/movies/v0/")
    public List<Movie> all() {
        return movieServiceSync.all();
    }
}
