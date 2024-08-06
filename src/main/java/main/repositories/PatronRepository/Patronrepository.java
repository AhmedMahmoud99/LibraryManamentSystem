package main.repositories.PatronRepository;
import main.models.PatronModel.Patron;
import main.repositories.BookRepository.BookRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class Patronrepository implements PatronDAO {
    private final PatronJpaRepository patronJpaRepository;

    public Patronrepository(PatronJpaRepository patronJpaRepository) {
        this.patronJpaRepository = patronJpaRepository;
    }

    @Override
    public List<Patron> GetAllPatrons() {
        return patronJpaRepository.findAll() ;
    }

    @Override
    public void InsertPatron(Patron patron) {
    patronJpaRepository.save(patron);
    }

    @Override
    public Optional<Patron> GetPatronById(Integer id) {
        return patronJpaRepository.findById(id);
    }

    @Override
    public boolean ExistPatronWithId(Integer id) {
        return patronJpaRepository.existsById(id);
    }

    @Override
    public void UpdatePatron(Patron patron) {
    patronJpaRepository.save(patron);
    }

    @Override
    public void DeletePatron(Integer id) {
    patronJpaRepository.deleteById(id);
    }
    public Patron GetPatron(Integer id) {
        return patronJpaRepository.getReferenceById(id);
    }

}
