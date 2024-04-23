package sep.model;

public class Borrowed implements State {

    @Override
    public void borrow(Book book, User user) {
        throw new RuntimeException("Book is already borrowed"); // In JavaFX, we will just disable the buttons that will throw it
    }

    @Override
    public void returnBook(Book book, User user) {
        if(book.getBorrower().equals(user))
        {
            book.setState(new Available());
            System.out.println(book.getTitle() + " got returned by " + user.getFirstName());
            book.setBorrower(null);
        }
        else
            throw new RuntimeException("You cannot return book that you do not have");
    }

    @Override
    public void reserve(Book book, User user) {
        if(!book.getBorrower().equals(user))
        {
            book.setState(new Borrowed_Reserved());
            System.out.println(book.getTitle() + " got reserved by " + user.getFirstName());
            book.setReservist(user);
        }
        else
            throw new RuntimeException("You cannot reserve the book that you borrowed");
    }
}
