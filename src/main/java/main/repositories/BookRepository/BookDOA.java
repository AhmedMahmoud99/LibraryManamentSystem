package main.repositories.BookRepository;

import main.models.BookModel.Book;

import java.util.List;
import java.util.Optional;

public interface BookDOA {
    List<Book> GetAllBooks();

    void InsertBook(Book book);

    Optional<Book> GetBookById(Integer id);

    boolean ExistBookWithId(Integer id);

    void UpdateBook(Book book);

    void DeleteBook(Integer id);
}
