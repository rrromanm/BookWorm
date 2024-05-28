package sep.model;

import java.io.Serializable;
/**
 * Represents the state of a book when it is borrowed.
 * Implements the {@link State} interface and {@link Serializable} for object serialization.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */

public class Borrowed implements State, Serializable {

    /**
     * Throws an exception since a borrowed book cannot be borrowed again.
     *
     * @param book   the book to be borrowed
     * @param patron the patron who is attempting to borrow the book
     * @throws RuntimeException if the book is already borrowed
     */
    @Override
    public void borrow(Book book, Patron patron) {
        throw new RuntimeException("Book is already borrowed");
    }

    /**
     * Returns the book if it was borrowed by the given patron and changes its state to Available.
     *
     * @param book   the book to be returned
     * @param patron the patron who is returning the book
     * @throws RuntimeException if the book was not borrowed by the patron or if the book cannot be returned
     */
    @Override
    public void returnBook(Book book, Patron patron) {
        if(book.getBorrower().equals(patron))
        {
            book.setState(new Available());
            System.out.println(book.getTitle() + " got returned by " + patron.getFirstName());
            book.setBorrower(null);
        }
        else
            throw new RuntimeException("You cannot return book that you do not have");
    }

    /**
     * Returns a string representation of the state.
     *
     * @return a string indicating the state is "Borrowed"
     */
    public String toString()
    {
        return "Borrowed";
    }
}
