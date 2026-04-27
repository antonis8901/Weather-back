package org.example.bookauthor.dto.author;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO {

    private Long id;
    private String name;
    private String ethnicity;
    private String dateOfBirth;

    // Only return book titles in the author response (avoid infinite nesting)
    private List<String> bookTitles;
}