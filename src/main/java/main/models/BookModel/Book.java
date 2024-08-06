package main.models.BookModel;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@SequenceGenerator(name = "book_seq", sequenceName = "book_sequence", allocationSize = 1)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Integer id;
    private Integer book_pub_year;
    private String book_title;
    private String book_author;
    private String book_isbn;
    public Book(Integer id){
        this.id=id;
    }
}
