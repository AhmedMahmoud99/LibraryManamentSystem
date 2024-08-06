package main.Services.PatronService;
import main.MyException.RequestValidationException;
import main.MyException.ResourceNotFound;
import main.Services.BookService.BookJpaDatabaseService;
import main.models.PatronModel.Patron;
import main.repositories.BookRepository.BookRepository;
import main.repositories.PatronRepository.Patronrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PatronJpaDatabaseService {

    private static final Logger logger= LoggerFactory.getLogger(PatronJpaDatabaseService.class);
    private final Patronrepository Patronepository;
    public PatronJpaDatabaseService(Patronrepository Patronepository) {
        this.Patronepository = Patronepository;
    }
    @Cacheable("patrons")
    public List<Patron> GetAllPatrons() {
        logger.info("Get All patrons from database");
        return Patronepository.GetAllPatrons();
    }

    @Cacheable(value = "patrons",key = "#id")
    public Patron GetPatronById(Integer id) {
        logger.info("Get patron with if {} from database",id);
        return Patronepository.GetPatronById(id).orElseThrow(() -> new ResourceNotFound("Patron with this ID is not found".formatted(id)));
    }
    @CacheEvict(value = "patrons",key = "#Patron")
    public void AddPatron(Patron Patron) {
        if (Patron.getContactInformation().getPhone_number().length() != 11
                || !Patron.getContactInformation().getPhone_number().startsWith("01")) {
            throw new RequestValidationException("Enter a valid number");
        }
        else
            Patronepository.InsertPatron(Patron);
    }
    @Cacheable(value = "patrons",key = "#id")
    public void UpdatePatron(Integer id, Patron patronRegister) {
        Patron Patron = Patronepository.GetPatron(id);
        boolean change_flag = false;
        if (patronRegister.getPatron_name()!= null && !patronRegister.getPatron_name().equals(Patron.getPatron_name())) {
            Patron.setPatron_name(patronRegister.getPatron_name());
            change_flag = true;
        }
        if ((patronRegister.getContactInformation().getPhone_number()!= null &&
                !patronRegister.getContactInformation().getPhone_number().
                equals(Patron.getContactInformation().getPhone_number()))||(
                patronRegister.getContactInformation().getAddress()!= null &&
                !patronRegister.getContactInformation().getAddress().
                equals(Patron.getContactInformation().getAddress())))
        {
            Patron.setContactInformation(patronRegister.getContactInformation());
            change_flag = true;
        }
        if (!change_flag) {
            throw new RequestValidationException("No data changed");
        }
        Patronepository.UpdatePatron(Patron);
    }
    @Cacheable(value = "patrons",key = "#id")
    public void DeletePatronById(Integer id) {
        System.out.println(Patronepository.ExistPatronWithId(id));
        if (Patronepository.ExistPatronWithId(id)) {
            Patronepository.DeletePatron(id);
        } else {
            throw new ResourceNotFound("No Patron found");
        }
    }
}
