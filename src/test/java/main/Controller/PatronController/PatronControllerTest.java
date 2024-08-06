package main.Controller.PatronController;

import main.Services.PatronService.PatronJpaDatabaseService;
import main.models.PatronModel.ContactInformation;
import main.models.PatronModel.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatronControllerTest {
    @Mock
    private PatronJpaDatabaseService patronJpaDatabaseService;
    @InjectMocks
    private PatronController patronController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatrons() {
        List<Patron>expected_patrons= Arrays.asList(new Patron(),new Patron());
        when(patronJpaDatabaseService.GetAllPatrons()).thenReturn(expected_patrons);
        List<Patron>actual_patrons= patronController.GetAllPatrons();
        assertEquals(expected_patrons,actual_patrons);
        verify(patronJpaDatabaseService).GetAllPatrons();
    }

    @Test
    void getPatronById() {
        Integer id=1;
        patronController.GetPatronById(id);
        verify(patronJpaDatabaseService).GetPatronById(id);
    }

    @Test
    void addPatron() {
        Patron patron=new Patron(1,"Ahmed",new ContactInformation(1,"2RE","01123458456" ));
        patronController.AddPatron(patron);
        verify(patronJpaDatabaseService).AddPatron(patron);
    }

    @Test
    void updatePatron() {
        Integer id=1;
        Patron patron=new Patron(1,"Jad",new ContactInformation(1,"2TE","01123458456" ));
        patronController.UpdatePatron(id,patron);
        verify(patronJpaDatabaseService).UpdatePatron(id,patron);
    }

    @Test
    void deletePatron() {
        Integer id=1;
        patronController.DeletePatron(id);
        verify(patronJpaDatabaseService).DeletePatronById(id);
    }
}