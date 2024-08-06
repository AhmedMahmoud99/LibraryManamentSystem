package main.Controller.BookController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import main.Services.BookService.BookJpaDatabaseService;
import main.models.BookModel.Book;
import main.models.BookModel.BookUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("api/")
@Validated
public class BookController {
    @Autowired
    private final BookJpaDatabaseService jpaDatabase;
    public BookController(BookJpaDatabaseService jpaDatabase) {
        this.jpaDatabase =jpaDatabase;
    }
    @GetMapping("GetAllBooks")
    @PreAuthorize("hasRole('USER')")
    public List<Book> GetAllBooks() {
        return jpaDatabase.GetAllBooks();
    }
    @GetMapping("GetBookById/{id}")
    @PreAuthorize("hasRole('USER')")
    public Book GetBookById(@PathVariable("id") Integer id) {
        return jpaDatabase.GetBookById(id);
    }
    @PostMapping("AddNewBook")
    @PreAuthorize("hasRole('ADMIN')")
    public void AddBook(@RequestBody Book book){
        jpaDatabase.AddBook(book);
    }
    @PutMapping("UpdateBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void UpdateBook(@PathVariable Integer id,@Valid @RequestBody BookUpdate bookUpdate){
        jpaDatabase.UpdateBook(id, bookUpdate);
    }
    @DeleteMapping("DeleteBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteBook(@PathVariable Integer id){
        jpaDatabase.DeleteBookById(id);
    }

}
