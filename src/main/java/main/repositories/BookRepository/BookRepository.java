package main.repositories.BookRepository;

import main.models.BookModel.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository implements BookDOA {
    private final BookJpaRepository bookJpaRepository;

    public BookRepository(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public List<Book> GetAllBooks() {
        return bookJpaRepository.findAll();
    }

    @Override
    public void InsertBook(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> GetBookById(Integer id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public boolean ExistBookWithId(Integer id) {
        return bookJpaRepository.existsById(id);
    }

    @Override
    public void UpdateBook(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public void DeleteBook(Integer id) {
        bookJpaRepository.deleteById(id);
    }

    public Book GetBook(Integer id) {
        return bookJpaRepository.getReferenceById(id);
    }
}
