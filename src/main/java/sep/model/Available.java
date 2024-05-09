package sep.model;

import java.io.Serializable;

public class Available implements State, Serializable {


    @Override
    public void borrow(Book book, Patron patron) {
        book.setState(new Borrowed());
        System.out.println(book.getTitle() + " got borrowed by " + patron.getFirstName());
        book.setBorrower(patron);
    }

    @Override
    public void returnBook(Book book, Patron patron) {
        throw new RuntimeException("The book is available you cannot return it"); // In JavaFX, we will just disable the buttons that will throw it
    }
    public String toString()
    {
        return "available";
    }
}
