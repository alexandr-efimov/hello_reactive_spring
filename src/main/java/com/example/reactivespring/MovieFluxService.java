package com.example.reactivespring;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by alexandr.efimov@sigma.software on 10/3/2017.
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieFluxService {

    private MovieRepository movieRepository;

    public Flux<Movie> all() {
        return movieRepository.findAll();
    }

    public Flux<MovieEvent> streamStreams(Movie movie) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<MovieEvent> events = Flux.fromStream(Stream.generate(() -> MovieEvent.of(movie, Instant.now(),
                randomUser())));

        return Flux.zip(interval, events).map(Tuple2::getT2);

    }

    public Mono<Movie> byId(String id) {
        return movieRepository.findById(id);
    }

    private String randomUser() {
        String usernamePrefix = "user";
        List<String> users = IntStream.range(0, 5).mapToObj(i -> usernamePrefix + i).collect(Collectors.toList());

        return users.get(new Random().nextInt(users.size()));
    }
}
