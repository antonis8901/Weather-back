package org.example.bookauthor.domain.models.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // best for MySQL
    private Integer id;

    // ISBN stays as a unique business identifier, NOT the PK
    @Column(name = "isbn", nullable = false, unique = true, length = 13) //Cannot be null
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column
    private String category;

    @Column(name = "release_date")
    private String releaseDate;

    // Book is the OWNING side: defines the join table
    @ManyToMany
    @JoinTable(
            name = "book_author",                                    // join table name
            joinColumns = @JoinColumn(name = "book_id"),             // FK to Book
            inverseJoinColumns = @JoinColumn(name = "author_id")     // FK to Author
    )
    @Builder.Default
    private List<Author> authors = new ArrayList<>();
}