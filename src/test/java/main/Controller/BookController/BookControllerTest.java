package main.Controller.BookController;

import main.Services.BookService.BookJpaDatabaseService;
import main.models.BookModel.Book;
import main.models.BookModel.BookUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookControllerTest {
    @Mock
    private BookJpaDatabaseService bookJpaDatabaseService;
    @InjectMocks
    private BookController bookController;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllBooks() {
        List<Book>expected_books= Arrays.asList(
                new Book(),new Book()
        );
        when(bookJpaDatabaseService.GetAllBooks()).thenReturn(expected_books);
        List<Book> actual_books=bookController.GetAllBooks();
        assertEquals(expected_books, actual_books);
        verify(bookJpaDatabaseService).GetAllBooks();
    }

    @Test
    void getBookById() {
        Integer id=1;
        bookController.GetBookById(id);
        verify(bookJpaDatabaseService).GetBookById(id);
    }

    @Test
    void addBook() {
        Book book=new Book(1,2022,"THINKing","Ahmed Mahmoud","A15869");
        bookController.AddBook(book);
        verify(bookJpaDatabaseService).AddBook(book);
    }

    @Test
    void updateBook() {
        Integer id=1;
        BookUpdate bookUpdate=new BookUpdate(2022,"THINKing","Ahmed","A15896");
        bookController.UpdateBook(id,bookUpdate);
        verify(bookJpaDatabaseService).UpdateBook(id,bookUpdate);
    }

    @Test
    void deleteBook() {
        Integer id=1;
        bookController.DeleteBook(id);
        verify(bookJpaDatabaseService).DeleteBookById(id);
    }
}