package org.example.bookauthor.domain.services;

import lombok.RequiredArgsConstructor;
import org.example.bookauthor.domain.models.entities.Author;
import org.example.bookauthor.domain.models.entities.Book;
import org.example.bookauthor.domain.repositories.AuthorsRepository;
import org.example.bookauthor.dto.author.AuthorRequestDTO;
import org.example.bookauthor.dto.author.AuthorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorsRepository authorRepository;

    // ===== CREATE =====
    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        Author author = Author.builder()
                .name(dto.getName())
                .ethnicity(dto.getEthnicity())
                .dateOfBirth(dto.getDateOfBirth())
                .build();

        return toResponseDTO(authorRepository.save(author));
    }

    // ===== READ ALL =====
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ===== READ ONE =====
    public AuthorResponseDTO getAuthorById(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        return toResponseDTO(author);
    }

    // ===== UPDATE =====
    public AuthorResponseDTO updateAuthor(Integer id, AuthorRequestDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        author.setName(dto.getName());
        author.setEthnicity(dto.getEthnicity());
        author.setDateOfBirth(dto.getDateOfBirth());

        return toResponseDTO(authorRepository.save(author));
    }

    // ===== DELETE =====
    public void deleteAuthor(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    // ===== MAPPER: Entity → ResponseDTO =====
    private AuthorResponseDTO toResponseDTO(Author author) {
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .ethnicity(author.getEthnicity())
                .dateOfBirth(author.getDateOfBirth())
                .bookTitles(
                        author.getBooks().stream()
                                .map(Book::getTitle)
                                .toList()
                )
                .build();
    }
}