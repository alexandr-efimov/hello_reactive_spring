package com.example.reactivespring.synchronous;

import com.example.reactivespring.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alexandr.efimov@sigma.software on 10/4/2017.
 */
@Service
public class MovieServiceSync {

    @Autowired
    private MovieRepositorySync movieRepositorySync;

    public List<Movie> all() {
        return movieRepositorySync.findAll();
    }
}
