package org.example.bookauthor.domain.repositories;

import org.example.bookauthor.domain.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
