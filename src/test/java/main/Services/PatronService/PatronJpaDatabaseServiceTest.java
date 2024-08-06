package main.Services.PatronService;

import main.MyException.DuplicateDFound;
import main.MyException.RequestValidationException;
import main.MyException.ResourceNotFound;
import main.models.BookModel.Book;
import main.models.BookModel.BookUpdate;
import main.models.PatronModel.ContactInformation;
import main.models.PatronModel.Patron;
import main.repositories.PatronRepository.Patronrepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronJpaDatabaseServiceTest {
    @Mock
    private Patronrepository patronrepository;
    @InjectMocks
    private PatronJpaDatabaseService patronJpaDatabaseService;
    private Patron patron;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patron=new Patron();
        patron.setId(1);
        patron.setPatron_name("Ahmed");
        patron.setContactInformation(new ContactInformation(1,"8AC","01187456932"));
    }

    @Test
    void getAllPatrons() {
        List<Patron>expected_patrons= Arrays.asList(patron);
        when(patronrepository.GetAllPatrons()).thenReturn(expected_patrons);
        List<Patron>actual_patrons=patronJpaDatabaseService.GetAllPatrons();
        assertEquals(expected_patrons,actual_patrons);
    }


    @Test
    void getPatronByIdFailed() {
        when(patronrepository.ExistPatronWithId(1)).thenReturn(false);
        assertThrows(ResourceNotFound.class, () -> patronJpaDatabaseService.GetPatronById(1));
    }



    @Test
    public void testUpdateBook_WhenNoChanges_ThrowsRequestValidationException() {
        Integer id = 1;
        patron.setPatron_name("Ahmed");
        patron.setContactInformation(new ContactInformation(1,"8AX","01234567891"));
        Patron newpatron = new Patron();
        newpatron.setPatron_name("Ahmed");
        newpatron.setContactInformation(new ContactInformation(1,"8AX","01234567891"));
        when(patronrepository.GetPatron(id)).thenReturn(patron);
        assertThrows(RequestValidationException.class, () -> patronJpaDatabaseService.UpdatePatron(id, newpatron));
        verify(patronrepository, never()).UpdatePatron(patron);
    }

    @Test
    void deletePatronById_Success() {
        when(patronrepository.ExistPatronWithId(1)).thenReturn(true);
        assertDoesNotThrow(() -> patronJpaDatabaseService.DeletePatronById(1));
        verify(patronrepository).DeletePatron(1);
    }

    @Test
    void deletePatronById_dNotFound() {
        when(patronrepository.ExistPatronWithId(1)).thenReturn(false);
        assertThrows(ResourceNotFound.class, () -> patronJpaDatabaseService.DeletePatronById(1));
    }

}