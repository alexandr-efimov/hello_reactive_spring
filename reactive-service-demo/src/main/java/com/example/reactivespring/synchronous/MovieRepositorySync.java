package com.example.reactivespring.synchronous;

import com.example.reactivespring.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by alexandr.efimov@sigma.software on 10/4/2017.
 */
public interface MovieRepositorySync extends MongoRepository<Movie, String> {
}
