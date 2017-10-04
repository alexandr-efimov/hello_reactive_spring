package com.example.reactivespring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class HelloReactiveSpringApplication {

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloReactiveSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDemoData() {
        return args -> movieRepository.deleteAll()
                .subscribe(null, null,
                        () -> Stream.of("Reactive", "Flux", "Spring5", "Future", "Wtf?")
                                .map(title -> Movie.of(null, title, randomGenre()))
                                .forEach(s -> movieRepository.save(s).subscribe(System.out::println)));
    }

    private String randomGenre() {
        String[] genres = "horror,comedy,drama,fantasy".split(",");
        return genres[new Random().nextInt(genres.length)];
    }

    @Bean
    public RouterFunction<ServerResponse> routes(MovieFluxService service) {
        return route(GET("/movies/flux"),
                request -> {
                    System.out.println("__FLUX WITH ===NEW===! STYLE");
                    return ok().body(service.all(), Movie.class);
                })

                .andRoute(GET("movies/v2/{id}"),
                        request -> ok().body(service.byId(request.pathVariable("id")), Movie.class))

                .andRoute(GET("/movies/v2/{id}/events"),
                        request -> ok()
                                .contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(service.byId(request.pathVariable("id"))
                                        .map(service::streamStreams), Flux.class));
    }
}
