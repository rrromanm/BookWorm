package sep.model;

public class Borrowed_Reserved implements State{

    @Override
    public void borrow(Book book, User user) {
        throw new RuntimeException(book.getTitle() + " is borrowed");
    }

    @Override
    public void returnBook(Book book, User user) {
        if(book.getBorrower().equals(user))
        {
            book.setState(new Reserved());
            System.out.println(book.getTitle() + " got returned by " + user.getFirstName() + " but it is still reserved");
            book.setBorrower(null);
        }
        else
            throw new RuntimeException("You cannot return book that you did not borrowed");

    }

    @Override
    public void reserve(Book book, User user) {
        throw new RuntimeException(book.getTitle() + " is reserved");
    }
}
