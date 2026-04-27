package org.example.bookauthor.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.bookauthor.domain.models.entities.Author;
import org.example.bookauthor.domain.models.entities.Book;
import org.example.bookauthor.domain.repositories.AuthorsRepository;
import org.example.bookauthor.domain.repositories.BooksRepository;
import org.example.bookauthor.dto.book.BookRequestDTO;
import org.example.bookauthor.dto.book.BookResponseDTO;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor  // Lombok: injects dependencies via constructor (no @Autowired needed)
public class BookService {

    private final BooksRepository bookRepository;
    private final AuthorsRepository authorRepository;

    // ===== CREATE =====
    public BookResponseDTO createBook(BookRequestDTO dto) {

        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("A book with ISBN " + dto.getIsbn() + " already exists");
        }

        // Fetch all authors by the IDs provided in the request
        List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
        if (authors.isEmpty()) {
            throw new RuntimeException("No valid authors found for the provided IDs");
        }

        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .category(dto.getCategory())
                .releaseDate(dto.getReleaseDate())
                .authors(authors)
                .build();

        Book saved = bookRepository.save(book);
        return toResponseDTO(saved);
    }

    // ===== READ ALL =====
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ===== READ ONE =====
    public BookResponseDTO getBookById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toResponseDTO(book);
    }

    // ===== UPDATE =====
    public BookResponseDTO updateBook(Integer id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
        if (authors.isEmpty()) {
            throw new RuntimeException("No valid authors found for the provided IDs");
        }

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setReleaseDate(dto.getReleaseDate());
        book.setAuthors(authors);

        return toResponseDTO(bookRepository.save(book));
    }

    // ===== DELETE =====
    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // ===== MAPPER: Entity → ResponseDTO =====
    private BookResponseDTO toResponseDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .category(book.getCategory())
                .releaseDate(book.getReleaseDate())
                .authorNames(
                        book.getAuthors().stream()
                                .map(Author::getName)
                                .toList()
                )
                .build();
    }
}