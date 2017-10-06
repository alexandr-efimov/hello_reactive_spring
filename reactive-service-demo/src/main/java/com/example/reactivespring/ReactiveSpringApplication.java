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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class ReactiveSpringApplication {

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDemoData() {
        return args -> movieRepository.deleteAll()
                .subscribe(null, null,
                        () -> Stream.of("Reactive", "Flux", "Spring5", "Future", "Wtf?")
                                .map(title -> Movie.of(null, title, randomGenre(), someTags(), someDescription()))
                                .forEach(s -> movieRepository.save(s).subscribe()));
    }

    private List<String> someTags() {
        return Arrays.asList(("It is convenient to distinguish roughly between three kinds of computer programs. " +
                "Transformational programs compute results from a given set of inputs; typical examples are compilers" +
                "  or numerical computation programs. Interactive programs interact at their own speed with users or " +
                " with other programs; from a user point of view, a time-sharing system is interactive. Reactive  " +
                "programs also maintain a continuous interaction with their environment, but at a  speed which is " +
                "determined by the environment, not the program itself. Interactive programs work at  their own pace " +
                "and mostly deal with communication, while reactive programs only work in response to  external " +
                "demands and mostly deal with accurate interrupt handling. Real-time programs are  usually reactive. " +
                "However, there are reactive programs that are not usually considered as being real-time,  such as " +
                "protocols, system drivers, or man-machine interface handlers. ").split(" "));
    }

    private List<String> someDescription() {
        return Arrays.asList("In computing, reactive programming is an asynchronous programming paradigm concerned  " +
                "with data streams and the propagation of change. This means that it becomes possible to express  " +
                "static (e.g. arrays) or dynamic (e.g. event emitters)  data streams with ease via the employed " +
                "programming language(s), and that an inferred dependency  within the associated execution model " +
                "exists, which facilitates the automatic propagation of the change involved with data flow.\n" +
                "For example, in an imperative programming setting,  {\\displaystyle a:=b+c} a:=b+c would mean that " +
                "{\\displaystyle a} a is being assigned the result of  {\\displaystyle b+c} b+c in the instant the " +
                "expression is evaluated, and later, the values of {\\displaystyle b} b and/or {\\displaystyle c}  c " +
                "can be changed with no effect on the value of {\\displaystyle a} a. However, in reactive " +
                "programming,  the value of {\\displaystyle a} a is automatically updated whenever the values of  " +
                "{\\displaystyle b} b and/or {\\displaystyle c} c change; without the program having to re-execute  " +
                "the sentence {\\displaystyle a:=b+c} a:=b+c to determine the presently assigned value of  " +
                "{\\displaystyle a} a.\n" +
                "Another example is a hardware description language such   as Verilog, where reactive programming " +
                "enables changes to be modeled as they propagate through  circuits. Consequently, reactive " +
                "programming has been proposed as a way to simplify the creation  of interactive user interfaces, " +
                "(near) real time system animation, but is constituted essentially as a general programming paradigm.\n" +
                "For example, in an model–view–controller (MVC) architecture,  reactive programming can facilitate " +
                "changes in an underlying model that  automatically are reflected in an associated view, and " +
                "contrarily.[1]".split(" "));
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
