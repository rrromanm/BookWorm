package sep.model;

/**
 * This interface represents the state of a book in the library system, defining actions that can be performed on a book.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public interface State {
    /**
     * Borrow a book from the library.
     *
     * @param book the book to be borrowed
     * @param patron the patron who is borrowing the book
     */
    void borrow(Book book, Patron patron);
    /**
     * Return a book to the library.
     *
     * @param book the book to be returned
     * @param patron the patron who is returning the book
     */
    void returnBook(Book book, Patron patron);
}
