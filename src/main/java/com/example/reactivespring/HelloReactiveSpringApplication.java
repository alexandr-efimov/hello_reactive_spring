package com.example.reactivespring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class HelloReactiveSpringApplication {

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloReactiveSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDemoData() {
        return args -> {
            movieRepository.deleteAll()
                    .subscribe(null, null,
                            () -> {
                                Stream.of("Reactive", "Flux", "Spring5", "Future", "Wtf?")
                                        .map(title -> Movie.of(null, title, randomGenre()))
                                        .forEach(s -> movieRepository.save(s).subscribe(System.out::println));
                            });
        };
    }

    private String randomGenre() {
        String[] genres = "horror,comedy,drama,fantasy".split(",");
        return genres[new Random().nextInt(genres.length)];
    }
}
