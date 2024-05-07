package sep.model;

public class Available implements State{


    @Override
    public void borrow(Book book, Patron patron) {
        throw new RuntimeException("You can not borrow without reserving the book"); // In JavaFX, we will just disable the buttons that will throw it
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
