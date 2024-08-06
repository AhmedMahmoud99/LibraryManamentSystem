package main.repositories.BookRepository;

import main.models.BookModel.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book,Integer>{
}
