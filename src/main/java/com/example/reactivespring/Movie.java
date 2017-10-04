package com.example.reactivespring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by alexandr.efimov@sigma.software on 10/3/2017.
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    @Id
    String id;

    String title;

    String genre;

    List<String> tags;

    List<String> description;
}
