package main.models.BorrowModel;
import jakarta.persistence.*;
import lombok.*;
import main.models.BookModel.Book;
import main.models.PatronModel.Patron;
import java.util.Date;
@Entity
@Setter
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@SequenceGenerator(name = "borrow_seq", sequenceName = "borrow_sequence", allocationSize = 1)
public class BorrowingRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrow_seq")
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;
}
