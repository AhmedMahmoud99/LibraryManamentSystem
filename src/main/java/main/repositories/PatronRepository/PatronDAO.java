package main.repositories.PatronRepository;

import main.models.BookModel.Book;
import main.models.PatronModel.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronDAO {
List<Patron> GetAllPatrons();
void InsertPatron(Patron patron);
Optional<Patron> GetPatronById(Integer id);
boolean ExistPatronWithId(Integer id);
void UpdatePatron(Patron patron);
void DeletePatron(Integer id);
}
