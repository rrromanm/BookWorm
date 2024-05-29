package sep.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.Book;
import sep.model.Model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminManageBooksViewModelTest {

    private Model model;
    private AdminManageBooksViewModel adminManageBooksViewModel;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        adminManageBooksViewModel = Mockito.mock(AdminManageBooksViewModel.class);
    }

    @Test
    public void testResetBookList() throws RemoteException {
        // Arrange
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Title 1", "Author 1", 2022, "Publisher 1", 1234567891235L, 100, "Genre 1"));
        books.add(new Book(2, "Title 2", "Author 2", 2023, "Publisher 2", 1234567891236L, 200, "Genre 2"));

        when(model.getAllBooks()).thenReturn(books);

        // Act
        assertDoesNotThrow(() -> adminManageBooksViewModel.resetBookList());

        // Assert
        verify(model, times(1)).getAllBooks();
    }

    @Test
    public void testShowFiltered() throws RemoteException {
        List<Book> filteredBooks = Arrays.asList(
                new Book(1, "Filtered Title 1", "Filtered Author 1", 2022, "Filtered Publisher 1", 1234567891235L, 100, "Filtered Genre 1"),
                new Book(2, "Filtered Title 2", "Filtered Author 2", 2023, "Filtered Publisher 2", 1234567891236L, 200, "Filtered Genre 2")
        );
        when(model.filter(anyString(), anyString(), anyString()));

        // Act
        assertDoesNotThrow(() -> adminManageBooksViewModel.showFiltered("Genre", "State", "Search"));

        // Assert
        verify(model, times(1)).filter("Genre", "State", "Search");
    }

    @Test
    public void testUpdateBook() throws SQLException, RemoteException {
        // Arrange
        int bookID = 1;
        String title = "Updated Title";
        String author = "Updated Author";
        String year = "2024";
        String publisher = "Updated Publisher";
        String isbn = "Updated ISBN";
        String pageCount = "150";
        String genre = "Updated Genre";

        // Act
        assertDoesNotThrow(() -> adminManageBooksViewModel.updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre));

        // Assert
        verify(model, times(1)).updateBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
    }

    @Test
    public void testDeleteBook() throws SQLException, RemoteException {
        // Arrange
        int bookID = 1;
        String title = "Title";
        String author = "Author";
        String year = "2024";
        String publisher = "Publisher";
        String isbn = "ISBN";
        String pageCount = "150";
        String genre = "Genre";

        // Act
        assertDoesNotThrow(() -> adminManageBooksViewModel.deleteBook(bookID, title, author, year, publisher, isbn, pageCount, genre));

        // Assert
        verify(model, times(1)).deleteBook(bookID, title, author, year, publisher, isbn, pageCount, genre);
    }

    @Test
    public void testCreateBook() throws SQLException, RemoteException {
        // Arrange
        String title = "New Title";
        String author = "New Author";
        String year = "2025";
        String publisher = "New Publisher";
        String isbn = "New ISBN";
        String pageCount = "200";
        String genre = "New Genre";

        // Act
        assertDoesNotThrow(() -> adminManageBooksViewModel.createBook(title, author, year, publisher, isbn, pageCount, genre));

        // Assert
        verify(model, times(1)).createBook(title, author, year, publisher, isbn, pageCount, genre);
    }
}
