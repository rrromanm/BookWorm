package sep.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Model;
import sep.model.Patron;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class AdminManageAccountsViewModel {
    private final Model model;
    private final ListProperty<Patron> patronList;
    private final SimpleObjectProperty<Patron> selectedPatron;

    public AdminManageAccountsViewModel(Model model) {
        this.model = model;
        this.patronList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedPatron = new SimpleObjectProperty<>();
    }
    public void bindList(ObjectProperty<ObservableList<Patron>> property) throws RemoteException {
        property.bindBidirectional(patronList);
    }

    public void bindSelectedBook(ReadOnlyObjectProperty<Patron> property) throws RemoteException {
        selectedPatron.bind(property);
    }
    public void loadPatrons() throws SQLException {
        List<Patron> patrons = PatronDatabaseImplementation.getInstance().getAllPatrons();
        patronList.setAll(patrons);
    }
}
