package sep.viewmodel;

import sep.model.Model;
import sep.model.Patron;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * The DonateViewModel class provides the view model for handling book donation operations.
 * It interacts with the model to donate a book with the provided details.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class DonateViewModel {
    private final Model model;

    /**
     * Constructs a DonateViewModel with the specified model.
     *
     * @param model The model to interact with for donating books
     */
    public DonateViewModel(Model model) {
        this.model = model;
    }

    /**
     * Donates a book with the provided details.
     *
     * @param title      The title of the book to donate
     * @param author     The author of the book to donate
     * @param isbn       The ISBN of the book to donate
     * @param year       The publication year of the book to donate
     * @param publisher  The publisher of the book to donate
     * @param pageCount  The page count of the book to donate
     * @param genre      The genre of the book to donate
     * @param patron     The patron donating the book
     * @throws SQLException  If an SQL exception occurs while donating the book
     * @throws IOException   If an I/O exception occurs while donating the book
     */
    public void donateBook(String title, String author, long isbn, int year, String publisher, int pageCount, String genre, Patron patron)
        throws SQLException, IOException
    {
        model.donateBook(title, author, isbn, year, publisher, pageCount, genre, patron);
    }
}
