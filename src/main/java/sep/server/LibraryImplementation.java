package sep.server;

import sep.jdbc.BookDatabaseImplementation;
import sep.model.Book;
import sep.shared.LibraryInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryImplementation implements LibraryInterface {
    private BookDatabaseImplementation bookDatabase;

    public LibraryImplementation() {
        try
        {
            this.bookDatabase = BookDatabaseImplementation.getInstance();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override public synchronized ArrayList<Book> getAllBooks() {
        try
        {
            return this.bookDatabase.readBooks();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
