package org.example.bookauthor.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookauthor.domain.services.BookService;
import org.example.bookauthor.dto.book.BookRequestDTO;
import org.example.bookauthor.dto.book.BookResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO dto) { // @RequestBody JSON -> BookRequestDTo
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(dto)); // @Valid runs DTO validation (that we've set)
    }

    @GetMapping // no id,we 'fetch' everything
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}") // id because we search for a specific one
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}") // needs @PathVariable to know which book to update
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Integer id, // which book to update
                                                      @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}