package main.Services.BorrowService;
import main.MyException.BookUnavailableException;
import main.MyException.BorrowLimitExceededException;
import main.MyException.RequestValidationException;
import main.MyException.ResourceNotFound;
import main.models.BookModel.Book;
import main.models.BorrowModel.BorrowingRecords;
import main.models.PatronModel.Patron;
import main.repositories.BookRepository.BookRepository;
import main.repositories.BorrowRepository.BorrowRecordRepository;
import main.repositories.PatronRepository.Patronrepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordJpaService {
    private static final int MAX_ALLOWED_BORROWINGS=5;
    private final BorrowRecordRepository borrowRecordRepository;
    private final Patronrepository patronJpaRepository;
    private final BookRepository bookJpaRepository;
    public BorrowRecordJpaService(BorrowRecordRepository borrowRecordRepository,
                                  Patronrepository patronJpaRepository,
                                  BookRepository bookJpaRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.patronJpaRepository = patronJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
    }
    @Transactional
    public BorrowingRecords AddBorrowBookRecord(Integer bookId, Integer patronId){
        Optional<BorrowingRecords> existingBorrow = borrowRecordRepository.findByBook_IdAndReturnDateIsNull(bookId);
        if (existingBorrow.isPresent()) {
            throw new BookUnavailableException("This book is not Available for now.");
        }
        long activeBorrowings = borrowRecordRepository.countByPatron_IdAndReturnDateIsNull(patronId);
        if (activeBorrowings >= MAX_ALLOWED_BORROWINGS) {
            throw new BorrowLimitExceededException("Patron has reached the maximum allowed borrowings.");
        }
        Book book = bookJpaRepository.GetBookById(bookId).orElseThrow(() -> new ResourceNotFound("Book not found"));
        Patron patron = patronJpaRepository.GetPatronById(patronId).orElseThrow(() -> new ResourceNotFound("Patron not found"));
        BorrowingRecords borrowingRecord = new BorrowingRecords();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(new Date());
        borrowingRecord.setDueDate(calendar.getTime());
        return borrowRecordRepository.save(borrowingRecord);
    }
    public BorrowingRecords UpdateReturnBook(Integer bookId, Integer patronId,Integer record_id)
    {
        BorrowingRecords borrowingRecord = borrowRecordRepository.
                findByBook_IdAndPatron_IdAndId(bookId,patronId,record_id)
                .orElseThrow(() -> new ResourceNotFound("Borrowing record not found"));
        if(borrowingRecord.getReturnDate()==null)
        {
            borrowingRecord.setReturnDate(new Date());
            return borrowRecordRepository.save(borrowingRecord);
        }
        else
            throw new RequestValidationException("NO Data to change");
    }
    public List<BorrowingRecords> getAllBorrowingRecords()
    {
        return borrowRecordRepository.findAll();
    }
    public void DeleteRecordById(Integer id)
    {
        if (borrowRecordRepository.existsById(id)) {
            borrowRecordRepository.deleteById(id);
        } else {
            throw new ResourceNotFound("No Record found");
        }
    }
}
