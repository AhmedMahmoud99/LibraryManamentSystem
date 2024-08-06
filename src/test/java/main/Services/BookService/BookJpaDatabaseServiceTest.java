package main.Services.BookService;
import main.MyException.DuplicateDFound;
import main.MyException.RequestValidationException;
import main.MyException.ResourceNotFound;
import main.models.BookModel.Book;
import main.models.BookModel.BookUpdate;
import main.repositories.BookRepository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class BookJpaDatabaseServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookJpaDatabaseService bookJpaDatabaseService;
    private Book book;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book=new Book();
        book.setId(1);
        book.setBook_pub_year(2022);
        book.setBook_title("THinking");
        book.setBook_author("AhmedMahmoud");
        book.setBook_isbn("A18562");
    }

    @Test
    void getAllBooks() {
        List<Book>expected_book= Arrays.asList(book);
        when(bookRepository.GetAllBooks()).thenReturn(expected_book);
        List<Book>actual_book=bookJpaDatabaseService.GetAllBooks();
        assertEquals(expected_book,actual_book);
    }

    @Test
    void getBookByIdSuccess() {
        when(bookRepository.ExistBookWithId(1)).thenReturn(true);
        assertDoesNotThrow(() -> bookJpaDatabaseService.GetBookById(1));
        verify(bookRepository).GetBookById(1);
    }
    @Test
    void getBookByIdFailed() {
        when(bookRepository.ExistBookWithId(1)).thenReturn(false);
        assertThrows(ResourceNotFound.class, () -> bookJpaDatabaseService.GetBookById(1));
    }
    @Test
    void addBook() {
        Book book = new Book();
        when(bookRepository.ExistBookWithId(book.getId())).thenReturn(false);
        bookJpaDatabaseService.AddBook(book);
        verify(bookRepository, times(1)).InsertBook(book);
    }
    @Test
    public void testAddBook_WhenBookExists_ThrowsDuplicateDFound(){
        Book book = new Book();
        book.setId(1);
        when(bookRepository.ExistBookWithId(book.getId())).thenReturn(true);
        assertThrows(DuplicateDFound.class, () -> bookJpaDatabaseService.AddBook(book));
        verify(bookRepository, never()).InsertBook(book);
    }

    @Test
    void updateBook() {
        Integer id = 1;
        BookUpdate bookUpdate = new BookUpdate(2021, "New Title", "New Author", "0987654321");
        when(bookRepository.GetBook(id)).thenReturn(book);
        bookJpaDatabaseService.UpdateBook(id, bookUpdate);
        verify(bookRepository, times(1)).UpdateBook(book);
        assertEquals(2021, book.getBook_pub_year());
        assertEquals("New Title", book.getBook_title());
        assertEquals("New Author", book.getBook_author());
        assertEquals("0987654321", book.getBook_isbn());

    }
    @Test
    public void testUpdateBook_WhenNoChanges_ThrowsRequestValidationException() {
        Integer id = 1;
        book.setBook_pub_year(2020);
        book.setBook_title("Original Title");
        book.setBook_author("Original Author");
        book.setBook_isbn("1234567890");
        BookUpdate bookUpdate = new BookUpdate(2020, "Original Title", "Original Author", "1234567890");
        when(bookRepository.GetBook(id)).thenReturn(book);
        assertThrows(RequestValidationException.class, () -> bookJpaDatabaseService.UpdateBook(id, bookUpdate));
        verify(bookRepository, never()).UpdateBook(book);
    }

    @Test
    void deleteBookByIdSuccess() {
        when(bookRepository.ExistBookWithId(1)).thenReturn(true);
        assertDoesNotThrow(() -> bookJpaDatabaseService.DeleteBookById(1));
        verify(bookRepository).DeleteBook(1);
    }

    @Test
    void deleteBookByIdFailed() {
        when(bookRepository.ExistBookWithId(1)).thenReturn(false);
        assertThrows(ResourceNotFound.class, () -> bookJpaDatabaseService.DeleteBookById(1));
    }
}