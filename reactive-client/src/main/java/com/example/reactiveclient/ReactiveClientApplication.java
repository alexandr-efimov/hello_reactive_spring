package com.example.reactiveclient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ReactiveClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(ReactiveClientApplication.class, args);
    }
}

@RestController
@Slf4j
class WebClientDemo {
    private WebClient webClient;

    public WebClientDemo() {
        ClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector();
        this.webClient = WebClient.builder().clientConnector(reactorClientHttpConnector).build();
    }

    @GetMapping("/testclient/")
    public Flux<Movie> test() {
        Flux<Movie> moviesFlux = this.webClient.get().uri("http://localhost:8000/movies/v1/")
                .retrieve()
                .bodyToFlux(Movie.class);

        moviesFlux.subscribe(movie -> log.info("Movie: {}", movie));
        return moviesFlux;
    }
}


@Data
@AllArgsConstructor
class MovieEvent {
    private Movie movie;
    private Date when;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Movie {
    String id;
    String title;
    String genre;
    List<String> tags;
    List<String> description;


    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", tags=" + String.join(",", tags) +
                ", description=" + description +
                '}';
    }
}