package sep.model;

public class Borrowed implements State {

    @Override
    public void borrow(Book book, Patron patron) {
        throw new RuntimeException("Book is already borrowed"); // In JavaFX, we will just disable the buttons that will throw it
    }

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
}
