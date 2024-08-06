package main.repositories.PatronRepository;

import main.models.PatronModel.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronJpaRepository extends JpaRepository<Patron,Integer> {
}
