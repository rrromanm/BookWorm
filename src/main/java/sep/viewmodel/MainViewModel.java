package sep.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sep.model.Book;
import sep.model.Model;

public class MainViewModel {
    private final Model model;
    private final ListProperty<Book> bookList;

    public MainViewModel(Model model)
    {
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public void bindList(ObjectProperty<ObservableList<Book>> property) {
        property.bindBidirectional(bookList);
    }
}
