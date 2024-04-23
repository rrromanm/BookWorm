package sep.model;

public class Available implements State{


    @Override
    public void borrow(Book book, User user) {
        throw new RuntimeException("You can not borrow without reserving the book"); // In JavaFX, we will just disable the buttons that will throw it
    }

    @Override
    public void returnBook(Book book, User user) {
        throw new RuntimeException("The book is available you cannot return it"); // In JavaFX, we will just disable the buttons that will throw it
    }

    @Override
    public void reserve(Book book, User user) {
        book.setState(new Reserved());
        book.setReservist(user);
        System.out.println(book.getTitle() + " got reserved by " + user.getFirstName());
    }
    public String toString()
    {
        return "available";
    }
}
