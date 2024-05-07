package sep.model;

public interface State {
    void borrow(Book book, Patron patron);
    void returnBook(Book book, Patron patron);
}
