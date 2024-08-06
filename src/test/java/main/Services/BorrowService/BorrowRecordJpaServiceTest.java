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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class BorrowRecordJpaServiceTest {
    @Mock
    private BorrowRecordRepository borrowRecordRepository;
    @Mock
    private BookRepository bookJpaRepository;
    @Mock
    private Patronrepository patronJpaRepository;
    @InjectMocks
    private BorrowRecordJpaService borrowRecordJpaService;
    private Book testBook;
    private Patron testPatron;
    private BorrowingRecords testBorrowingRecord;
    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1);
        testPatron = new Patron();
        testPatron.setId(1);
        testBorrowingRecord = new BorrowingRecords();
        testBorrowingRecord.setId(8);
        testBorrowingRecord.setBook(testBook);
        testBorrowingRecord.setPatron(testPatron);
        testBorrowingRecord.setBorrowDate(new Date());
    }
    //from line 50 to line 97 for add borrowing records
    @Test
    void AddBorrowBookRecord_SuccessfulBorrow() {
        when(borrowRecordRepository.findByBook_IdAndReturnDateIsNull(1)).thenReturn(Optional.empty());
        when(borrowRecordRepository.countByPatron_IdAndReturnDateIsNull(1)).thenReturn(0L);
        when(bookJpaRepository.GetBookById(1)).thenReturn(Optional.of(testBook));
        when(patronJpaRepository.GetPatronById(1)).thenReturn(Optional.of(testPatron));
        when(borrowRecordRepository.save(any(BorrowingRecords.class))).thenReturn(testBorrowingRecord);

        BorrowingRecords result = borrowRecordJpaService.AddBorrowBookRecord(1, 1);

        assertNotNull(result);
        assertEquals(testBook, result.getBook());
        assertEquals(testPatron, result.getPatron());
        assertNotNull(result.getBorrowDate());
        assertNull(result.getReturnDate());
        verify(borrowRecordRepository).save(any(BorrowingRecords.class));
    }

    @Test
    void addBorrowBookRecord_BookUnavailable() {
        when(borrowRecordRepository.findByBook_IdAndReturnDateIsNull(1)).thenReturn(Optional.of(testBorrowingRecord));
        assertThrows(BookUnavailableException.class, () -> borrowRecordJpaService.AddBorrowBookRecord(1, 1));
    }

    @Test
    void addBorrowBookRecord_BorrowLimitExceeded() {
        when(borrowRecordRepository.findByBook_IdAndReturnDateIsNull(1)).thenReturn(Optional.empty());
        when(borrowRecordRepository.countByPatron_IdAndReturnDateIsNull(1)).thenReturn(5L);

        assertThrows(BorrowLimitExceededException.class, () -> borrowRecordJpaService.AddBorrowBookRecord(1, 1));
    }

    @Test
    void addBorrowBookRecord_BookNotFound() {
        when(borrowRecordRepository.findByBook_IdAndReturnDateIsNull(1)).thenReturn(Optional.empty());
        when(borrowRecordRepository.countByPatron_IdAndReturnDateIsNull(1)).thenReturn(0L);
        when(bookJpaRepository.GetBookById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> borrowRecordJpaService.AddBorrowBookRecord(1, 1));
    }

    @Test
    void addBorrowBookRecord_PatronNotFound() {
        when(borrowRecordRepository.findByBook_IdAndReturnDateIsNull(1)).thenReturn(Optional.empty());
        when(borrowRecordRepository.countByPatron_IdAndReturnDateIsNull(1)).thenReturn(0L);
        when(bookJpaRepository.GetBookById(1)).thenReturn(Optional.of(testBook));
        when(patronJpaRepository.GetPatronById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> borrowRecordJpaService.AddBorrowBookRecord(1, 1));
    }
    //from line 100 to line 119 for updating
    @Test
    void updateReturnBook_Success() {
        testBorrowingRecord.setReturnDate(null);
        when(borrowRecordRepository.findByBook_IdAndPatron_IdAndId(1, 1, 1)).thenReturn(Optional.of(testBorrowingRecord));
        when(borrowRecordRepository.save(any(BorrowingRecords.class))).thenReturn(testBorrowingRecord);
        BorrowingRecords result = borrowRecordJpaService.UpdateReturnBook(1, 1, 1);
        assertNotNull(result.getReturnDate());
    }

    @Test
    void updateReturnBook_RecordNotFound() {
        when(borrowRecordRepository.findByBook_IdAndPatron_IdAndId(1, 1, 1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> borrowRecordJpaService.UpdateReturnBook(1, 1, 1));
    }

    @Test
    void updateReturnBook_AlreadyReturned() {
        testBorrowingRecord.setReturnDate(new Date());
        when(borrowRecordRepository.findByBook_IdAndPatron_IdAndId(1, 1, 1)).thenReturn(Optional.of(testBorrowingRecord));
        assertThrows(RequestValidationException.class, () -> borrowRecordJpaService.UpdateReturnBook(1, 1, 1));
    }
    //from line 122 to line 127 get all data
    @Test
    void getAllBorrowingRecords() {
        List<BorrowingRecords> expectedRecords = Arrays.asList(testBorrowingRecord);
        when(borrowRecordRepository.findAll()).thenReturn(expectedRecords);
        List<BorrowingRecords> result = borrowRecordJpaService.getAllBorrowingRecords();
        assertEquals(expectedRecords, result);
    }
    // from line 128 to 140 delete
    @Test
    void deleteRecordById_Success() {
        when(borrowRecordRepository.existsById(1)).thenReturn(true);
        assertDoesNotThrow(() -> borrowRecordJpaService.DeleteRecordById(1));
        verify(borrowRecordRepository).deleteById(1);
    }

    @Test
    void deleteRecordById_RecordNotFound() {
        when(borrowRecordRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFound.class, () -> borrowRecordJpaService.DeleteRecordById(1));
    }

    @AfterEach
    void tearDown() {
    }

}