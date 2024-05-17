package sep.viewmodel;

import sep.model.Model;
import sep.model.Patron;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class DonateViewModel {
    private final Model model;

    public DonateViewModel(Model model) {
        this.model = model;
    }

    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron) throws SQLException, RemoteException {
        model.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }
}
