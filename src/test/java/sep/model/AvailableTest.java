package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvailableTest {
    private Book book;
    private Patron patron;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
    }

    @Test
    public void borrowingChangesStateToBorrowedTest()
    {
        book.borrow(book, patron);
        assertTrue(book.getState() instanceof Borrowed);
    }

    @Test
    public void returnBookThrowsException()
    {
        assertThrows(RuntimeException.class, () -> {
            book.returnBook(book, patron);
        });
    }

    @Test
    public void toStringPrintsOutAvailable()
    {
        assertTrue(book.getState().toString().contains("Available"));
    }
}