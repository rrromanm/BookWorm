package sep.viewmodel;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DonateViewModelTest {

  private DonateViewModel viewModel;
  private Model model;

  @BeforeEach
  void setUp() {
    model = mock(Model.class);
    viewModel = new DonateViewModel(model);
  }


  @Test
  void donateBook_Success() throws SQLException, IOException {
    // Given
    Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
    Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

    // When
    viewModel.donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);

    // Then
    verify(model).donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);
  }

  @Test
  void donateBook_SQLException() throws SQLException, IOException {
    // Given
    Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
    Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

    doThrow(new SQLException("Test SQL Exception")).when(model).donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);

    // When & Then
    SQLException exception = assertThrows(SQLException.class, () -> {
      viewModel.donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);
    });

    assertEquals("Test SQL Exception", exception.getMessage());
    verify(model).donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);
  }

  @Test
  void donateBook_IOException() throws SQLException, IOException {
    // Given
    Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
    Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

    doThrow(new IOException("Test IO Exception")).when(model).donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);

    // When & Then
    IOException exception = assertThrows(IOException.class, () -> {
      viewModel.donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);
    });

    assertEquals("Test IO Exception", exception.getMessage());
    verify(model).donateBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getYear(), book.getPublisher(), book.getPageCount(), book.getGenre(), patron);
  }
}