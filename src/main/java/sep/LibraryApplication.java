package sep;

import sep.model.Model;
import sep.model.ModelManager;
import sep.shared.ConnectorInterface;
import sep.view.ViewHandler;
import sep.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

/**
 * Main class for the library application.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class LibraryApplication extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws IOException       If an I/O error occurs.
     * @throws NotBoundException If the requested name is not currently bound in the registry.
     * @throws SQLException      If a database access error occurs.
     */
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException, SQLException {
        Registry registry = LocateRegistry.getRegistry(1099);
        ConnectorInterface library = (ConnectorInterface) registry.lookup("library");
        Model model = new ModelManager(library);
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }

    /**
     * The entry point of the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}