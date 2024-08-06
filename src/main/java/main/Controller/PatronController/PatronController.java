package main.Controller.PatronController;
import jakarta.validation.Valid;
import main.Services.PatronService.PatronJpaDatabaseService;
import main.models.PatronModel.Patron;
import main.repositories.PatronRepository.PatronJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/")
@Validated
public class PatronController {
    @Autowired
    private final PatronJpaDatabaseService patronJpaDatabaseService;
    public PatronController(PatronJpaDatabaseService patronJpaDatabaseService, PatronJpaRepository repository) {
        this.patronJpaDatabaseService = patronJpaDatabaseService;
    }
    @GetMapping("GetAllPatrons")
    @PreAuthorize("hasRole('USER')")
    public List<Patron> GetAllPatrons() {
        return patronJpaDatabaseService.GetAllPatrons();
    }
    @GetMapping("GetPatronById/{id}")
    @PreAuthorize("hasRole('USER')")
    public Patron GetPatronById(@PathVariable("id") Integer id) {
        return patronJpaDatabaseService.GetPatronById(id);
    }
    @PostMapping("AddNewPatron")
    @PreAuthorize("hasRole('ADMIN')")
    public void AddPatron(@RequestBody Patron patron){
       patronJpaDatabaseService.AddPatron(patron);
    }
    @PutMapping("UpdatePatron/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void UpdatePatron(@PathVariable Integer id,@Valid @RequestBody Patron patronRegister){
        patronJpaDatabaseService.UpdatePatron(id, patronRegister);
    }
    @DeleteMapping("DeletePatron/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeletePatron(@PathVariable Integer id){
        patronJpaDatabaseService.DeletePatronById(id);
    }

}
