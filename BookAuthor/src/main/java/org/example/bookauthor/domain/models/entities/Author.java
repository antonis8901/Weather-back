package org.example.bookauthor.domain.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String ethnicity;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    // Author is the INVERSE side: just points back with mappedBy
    @ManyToMany(mappedBy = "authors")
    @Builder.Default
    private List<Book> books = new ArrayList<>();
}
