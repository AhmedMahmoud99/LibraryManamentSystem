package main.Services.BookService;
import main.MyException.DuplicateDFound;
import main.MyException.RequestValidationException;
import main.MyException.ResourceNotFound;
import main.repositories.BookRepository.BookRepository;
import main.models.BookModel.Book;
import main.models.BookModel.BookUpdate;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;

@Service
public class BookJpaDatabaseService {
    private static final Logger logger= LoggerFactory.getLogger(BookJpaDatabaseService.class);
    private final BookRepository bookRepository;

    public BookJpaDatabaseService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Cacheable("books")
    public List<Book> GetAllBooks() {
        logger.info("Fetch all books from database");
        return bookRepository.GetAllBooks();
    }
    @Cacheable(value = "books",key = "#id")
    public Book GetBookById(Integer id) {
        logger.info("Get book with if {} from database",id);
        return bookRepository.GetBookById(id).orElseThrow(() -> new ResourceNotFound("Book with this ID is not found".formatted(id)));
    }
    @CacheEvict(value = "books",key = "#book")
    public void AddBook(Book book) {
        if (bookRepository.ExistBookWithId(book.getId())) {
            throw new DuplicateDFound("Already existing book with this id");
        }
        bookRepository.InsertBook(book);

    }

    @CacheEvict(value = "books",key = "#id")
    public void UpdateBook(Integer id, BookUpdate bookUpdate) {
        Book book = bookRepository.GetBook(id);
        boolean change_flag = false;
        if (bookUpdate.book_pub_year() != null && !bookUpdate.book_pub_year().equals(book.getBook_pub_year())) {
            book.setBook_pub_year(bookUpdate.book_pub_year());
            change_flag = true;
        }

        if (bookUpdate.book_title() != null && !bookUpdate.book_title().equals(book.getBook_title())) {
            book.setBook_title(bookUpdate.book_title());
            change_flag = true;
        }

        if (bookUpdate.book_author() != null && !bookUpdate.book_author().equals(book.getBook_author())) {
            book.setBook_author(bookUpdate.book_author());
            change_flag = true;
        }

        if (bookUpdate.book_isbn() != null && !bookUpdate.book_isbn().equals(book.getBook_isbn())) {
            book.setBook_isbn(bookUpdate.book_isbn());
            change_flag = true;
        }
        if (!change_flag) {
            throw new RequestValidationException("No data changed");
        }
        bookRepository.UpdateBook(book);
    }

    @CacheEvict(value = "books",key = "#id")
    public void DeleteBookById(Integer id) {
        if (bookRepository.ExistBookWithId(id)) {
            bookRepository.DeleteBook(id);
        } else {
            throw new ResourceNotFound("No book found");
        }
    }
}
