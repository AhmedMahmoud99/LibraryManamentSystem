package main.Controller.BorrowContoller;

import main.Services.BorrowService.BorrowRecordJpaService;
import main.models.BorrowModel.BorrowingRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowBookRecordControllerTest {
    @Mock
    private BorrowRecordJpaService borrowRecordJpaService;
    @InjectMocks
    private BorrowBookRecordController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecords() {
        List<BorrowingRecords> expectedRecords = Arrays.asList(
                new BorrowingRecords(), new BorrowingRecords()
        );
        when(borrowRecordJpaService.getAllBorrowingRecords()).thenReturn(expectedRecords);

        // Act
        List<BorrowingRecords> actualRecords = controller.GetAllRecords();

        // Assert
        assertEquals(expectedRecords, actualRecords);
        verify(borrowRecordJpaService).getAllBorrowingRecords();
    }

    @Test
    void testAddBorrowRecord() {
        // Arrange
        Integer bookId = 1;
        Integer patronId = 2;

        // Act
        controller.AddBorrowRecord(bookId, patronId);

        // Assert
        verify(borrowRecordJpaService).AddBorrowBookRecord(bookId, patronId);
    }

    @Test
    void testReturnRecord() {
        // Arrange
        Integer recordId = 1;
        Integer bookId = 2;
        Integer patronId = 3;

        // Act
        controller.ReturnRecord(recordId, bookId, patronId);

        // Assert
        verify(borrowRecordJpaService).UpdateReturnBook(bookId, patronId, recordId);
    }

    @Test
    void testDeleteRecord() {
        // Arrange
        Integer id = 1;

        // Act
        controller.DeleteRecord(id);

        // Assert
        verify(borrowRecordJpaService).DeleteRecordById(id);
    }

}