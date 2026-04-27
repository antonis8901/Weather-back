package org.example.bookauthor.dto.book;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private Integer id;
    private String title;
    private String isbn;
    private String category;
    private String releaseDate;

    // Only return author names in the book response (avoid infinite nesting)
    private List<String> authorNames;
}