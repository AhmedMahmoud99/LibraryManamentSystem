package main.repositories.BorrowRepository;
import main.models.BorrowModel.BorrowingRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface BorrowRecordRepository extends JpaRepository<BorrowingRecords,Integer> {
    Optional<BorrowingRecords> findByBook_IdAndPatron_IdAndId(Integer bookId, Integer patronId,
                                                                               Integer borrowingrecordsId);
    Optional<BorrowingRecords> findByBook_IdAndReturnDateIsNull(Integer bookId);
    long countByPatron_IdAndReturnDateIsNull(Integer patronId);
    List<BorrowingRecords> findByPatron_IdAndReturnDateIsNullAndBorrowDateBefore(Integer patronId, Date date);



}
