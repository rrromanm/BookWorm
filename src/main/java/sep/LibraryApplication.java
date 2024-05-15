package sep;

import sep.model.Model;
import sep.model.ModelManager;
import sep.shared.LibraryInterface;
import sep.view.ViewHandler;
import sep.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LibraryApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        LibraryInterface library = (LibraryInterface) registry.lookup("library");
        Model model = new ModelManager(library);
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}