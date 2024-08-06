package main.Controller.BorrowContoller;
import main.Services.BorrowService.BorrowRecordJpaService;
import main.models.BorrowModel.BorrowingRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/")
public class BorrowBookRecordController {
    @Autowired
    private final BorrowRecordJpaService borrowRecordJpaService;
    public BorrowBookRecordController(BorrowRecordJpaService borrowRecordJpaService) {
        this.borrowRecordJpaService = borrowRecordJpaService;
    }

    @GetMapping("GetAllRecords")
    @PreAuthorize("hasRole('USER ')")
    public List<BorrowingRecords> GetAllRecords() {
        return borrowRecordJpaService.getAllBorrowingRecords();
    }
    @PostMapping("AddBorrowRecord/{book_id}/patron/{patron_id}")
    public void AddBorrowRecord(@PathVariable Integer book_id,@PathVariable Integer patron_id){
        borrowRecordJpaService.AddBorrowBookRecord(book_id,patron_id);
    }
    @PutMapping("ReturnRecord/{record_id}/Book/{book_id}/patron/{patron_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void ReturnRecord(@PathVariable Integer record_id,@PathVariable Integer book_id,@PathVariable Integer patron_id){
        borrowRecordJpaService.UpdateReturnBook(book_id, patron_id,record_id);
    }
    @DeleteMapping("DeleteRecord/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteRecord(@PathVariable Integer id){
        borrowRecordJpaService.DeleteRecordById(id);
    }

}
