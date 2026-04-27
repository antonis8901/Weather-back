package org.example.bookauthor.dto.book;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    @NotBlank(message = "Title is mandatory") //Cannot be null
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be exactly 13 digits")
    private String isbn;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @NotBlank(message = "Release date is mandatory")
    private String releaseDate;

    // Client sends a list of author IDs to associate with the book
    @NotEmpty(message = "A book must have at least one author")
    private List<Integer> authorIds;
}