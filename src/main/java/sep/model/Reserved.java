package sep.model;

public class Reserved implements State {

    @Override
    public void borrow(Book book, User user) {
        if(book.getReservist().equals(user))
        {
            book.setState(new Borrowed());
            System.out.println(book.getTitle() + " got borrowed by " + user);
            book.setBorrower(user);
            book.setReservist(null);
        }
        else
            throw new RuntimeException("Book is reserved by someone else"); // RuntimeException maybe will need to be replaced with something more suitable for JavaFX
    }

    @Override
    public void returnBook(Book book, User user) {
        throw  new RuntimeException("You cannot return the reserved book");
    }

    @Override
    public void reserve(Book book, User user) {
        throw new RuntimeException("Book is already reserved");
    }
    public String toString()
    {
        return "reserved";
    }
}
