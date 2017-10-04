package com.example.reactivespring;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by alexandr.efimov@sigma.software on 10/3/2017.
 */
@RestController
@RequestMapping("/movies/v1")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieRestController {

    private MovieFluxService movieFluxService;

    @GetMapping("/")
    public Flux<Movie> all() {

        System.out.println("__FLUX WITH OLD STYLE");
        return movieFluxService.all();
    }

    @GetMapping("/{id}")
    public Mono<Movie> byId(@PathVariable String id) {
        return movieFluxService.byId(id);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Flux<MovieEvent>> events(@PathVariable String id) {
        return movieFluxService.byId(id)
                .map(movieFluxService::streamStreams);

    }
}
