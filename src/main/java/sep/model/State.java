package sep.model;

//TODO user?, responsible JAKUB MATEUSZ ABRAMEK

public interface State {
    void borrow(Book book, User user);
    void returnBook(Book book,User user);
    void reserve(Book book,User user);
}
