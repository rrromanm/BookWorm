package sep.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sep.model.Book;
import sep.model.Model;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminManageDonatedBooksViewModelTest {
    @Mock
    private Model model;

    private AdminManageDonatedBooksViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new AdminManageDonatedBooksViewModel(model);
    }

    @Test
    void bindList() throws RemoteException {
        ObjectProperty<ObservableList<Book>> property = mock(ObjectProperty.class);
        viewModel.bindList(property);
        verify(property).bindBidirectional(viewModel.bookList);
    }

    @Test
    void approveDonatedBook() throws SQLException, RemoteException {
        viewModel.approveDonatedBook(1, "Title", "Author", 1234567890123L, 2024, "Publisher", 200, "Genre");
        verify(model).approveDonatedBook(1, "Title", "Author", 1234567890123L, 2024, "Publisher", 200, "Genre");
    }

    @Test
    void rejectDonatedBook() throws SQLException, RemoteException {
        viewModel.rejectDonatedBook(1);
        verify(model).rejectDonatedBook(1);
    }

    @Test
    void resetBookList() throws RemoteException {
        ArrayList<Book> bookList = model.getAllBooks();
        when(model.getDonatedBooks()).thenReturn(bookList);

        viewModel.resetBookList();

        assertEquals(bookList, viewModel.bookList);
        verify(model).getDonatedBooks();
    }
}
