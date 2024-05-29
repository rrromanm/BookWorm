package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowedTest {
    private Book book;
    private Patron patron;
    private Patron anotherPatron;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        book.setState(new Borrowed());

        patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        anotherPatron = new Patron(2, "Jane", "Doe", "janedoe", "password", "janedoe@example.com", "87654321", 0);
    }

    @Test
    void isBorrowed() {
        assertTrue(book.getState() instanceof Borrowed);
    }

    @Test
    void borrowThrowsException()
    {
        assertThrows(RuntimeException.class, () -> {
            book.borrow(book, patron);
        });
    }

    @Test
    void returnMakesBookAvailable()
    {
        book.setBorrower(patron);
        book.returnBook(book, patron);
        assertTrue(book.getState() instanceof Available);
    }

    @Test
    void returnThrowsExceptionWhenBorrowerIsNotTheSameAsBookBorrower()
    {
        book.setBorrower(null);
        assertThrows(RuntimeException.class, () -> {
            book.returnBook(book, patron);
        });
    }

    @Test
    void toStringDisplaysBorrowed()
    {
        assertTrue(book.getState().toString().contains("Borrowed"));
    }

    @Test
    void returningBookByDifferentPatronThrowsException()
    {
        book.setBorrower(patron);

        assertThrows(RuntimeException.class, () -> {
            book.returnBook(book, anotherPatron);
        });
    }

    @Test
    public void returningBookByCorrectPatronChangesStateToAvailable() {
        book.setBorrower(patron);
        book.returnBook(book, patron);
        assertTrue(book.getState() instanceof Available);
        assertNull(book.getBorrower());
    }
}