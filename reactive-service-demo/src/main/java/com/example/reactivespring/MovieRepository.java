package com.example.reactivespring;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alexandr.efimov@sigma.software on 10/3/2017.
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
}
