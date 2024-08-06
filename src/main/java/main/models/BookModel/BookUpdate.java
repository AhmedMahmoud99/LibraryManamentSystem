package main.models.BookModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookUpdate(@NotNull(message = "pub_year cant be null") Integer book_pub_year,
                         @NotBlank(message = "Cannot be blank")
                         @Size(max = 100,message = "cant be more than 50 characters")
                         String book_title,
                         @NotBlank(message = "Author cannot be blank")
                         @Size(max = 100, message = "Author cant be more than 50 characters")
                         String book_author,
                         @NotBlank(message = "ISBN cannot be blank")
                         @Size(max = 13, message = "ISBN cannot be more than 13 characters")
                         String book_isbn) {
}
