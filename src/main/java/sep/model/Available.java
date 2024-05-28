package sep.model;

import java.io.Serializable;

/**
 * Represents the state of a book when it is available to be borrowed.
 * Implements the {@link State} interface and {@link Serializable} for object serialization.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class Available implements State, Serializable {

    /**
     * Changes the state of the book to Borrowed and assigns the borrower.
     *
     * @param book   the book to be borrowed
     * @param patron the patron who is borrowing the book
     */
    @Override
    public void borrow(Book book, Patron patron) {
        book.setState(new Borrowed());
        System.out.println(book.getTitle() + " got borrowed by " + patron.getFirstName());
        book.setBorrower(patron);
    }

    /**
     * Throws an exception since an available book cannot be returned.
     *
     * @param book   the book to be returned
     * @param patron the patron who is attempting to return the book
     * @throws RuntimeException if the book is attempted to be returned while in available state
     */
    @Override
    public void returnBook(Book book, Patron patron) {
        throw new RuntimeException("The book is available you cannot return it");
    }
    /**
     * Returns a string of the state.
     *
     * @return a string indicating the state is "Available"
     */
    public String toString()
    {
        return "Available";
    }
}
