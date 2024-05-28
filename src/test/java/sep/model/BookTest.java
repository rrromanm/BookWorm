package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;
    private Patron patron;

    @BeforeEach
    void setUp()
    {
        book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
    }

    @Test
    void borrowingChangesStateToBorrowedTest() {
        book.borrow(book, patron);
        assertTrue(book.getState() instanceof Borrowed);
    }

    @Test
    void borrowingAssignsCorrectBorrower() {
        book.borrow(book, patron);
        assertEquals(patron, book.getBorrower());
    }

    @Test
    void returningThrowsExceptionWhenBookIsAvailable() {
        assertThrows(RuntimeException.class, () -> {
            book.returnBook(book, patron);
        });
    }

    @Test
    void settingAndGettingTitle() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    void settingAndGettingAuthor() {
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    void settingAndGettingYear() {
        book.setYear(2025);
        assertEquals(2025, book.getYear());
    }

    @Test
    void settingAndGettingPublisher() {
        book.setPublisher("New Publisher");
        assertEquals("New Publisher", book.getPublisher());
    }

    @Test
    void settingAndGettingIsbn() {
        book.setIsbn(9876543210987L);
        assertEquals(9876543210987L, book.getIsbn());
    }

    @Test
    void settingAndGettingPageCount() {
        book.setPageCount(300);
        assertEquals(300, book.getPageCount());
    }

    @Test
    void settingAndGettingBookId(){
        book.setBookId(2);
        assertEquals(2, book.getBookId());
    }

    @Test
    void settingAndGettingGenre() {
        book.setGenre("New Genre");
        assertEquals("New Genre", book.getGenre());
    }

    @Test
    void settingAndGettingReturnDate() {
        book.setReturnDate("2024-12-31");
        assertEquals("2024-12-31", book.getReturnDate());
    }

    @Test
    void equalsWithSameBorrower() {
        Book anotherBook = new Book(2, "Another Title", "Another Author", 2024, "Another Publisher", 9876543210123L, 250, "Another Genre");
        anotherBook.setBorrower(patron);
        book.setBorrower(patron);
        assertEquals(book, anotherBook);
    }

    @Test
    void notEqualsWithDifferentBorrowers() {
        Book anotherBook = new Book(2, "Another Title", "Another Author", 2024, "Another Publisher", 9876543210123L, 250, "Another Genre");
        Patron anotherPatron = new Patron(2, "Jane", "Doe", "janedoe", "password456", "janedoe@example.com", "87654321", 0);
        anotherBook.setBorrower(anotherPatron);
        book.setBorrower(patron);
        assertNotEquals(book, anotherBook);
    }

    @Test
    void notEqualsWithNull()
    {
        assertNotEquals(book, null);
    }

    @Test
    void notEqualsWithDifferentObject(){
        assertNotEquals(book, new Object());
    }

    @Test
    void equalsWhenComparedWithItself()
    {
        assertEquals(book, book);
    }

    @Test
    void toStringReturnsExpectedFormat() {
        String expectedString = "Book{title='Book Title', author='Author Name', publisher='Publisher Name', year=2024, isbn=1234567890123, pageCount=200, bookID=1, genre='Genre'}";
        assertEquals(expectedString, book.toString());
    }
}