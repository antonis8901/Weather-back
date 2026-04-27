package org.example.bookauthor.dto.author;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Ethnicity is mandatory")
    private String ethnicity;

    @NotBlank(message = "Date of birth is mandatory")
    private String dateOfBirth;
}