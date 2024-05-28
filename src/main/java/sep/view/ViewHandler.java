package sep.view;

import sep.viewmodel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * The ViewHandler class manages the navigation and display of views in the application.
 * It provides methods for opening and closing views based on their identifiers.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class ViewHandler {
    private final Scene currentScene;
    private Stage primaryStage;
    private final ViewFactory viewFactory;

    /**
     * Constructs a new ViewHandler object with the specified ViewModelFactory.
     *
     * @param viewModelFactory the ViewModelFactory used to create view models for the views
     */
    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewFactory = new ViewFactory(this, viewModelFactory);
        this.currentScene = new Scene(new Region());
    }

    /**
     * Sets up the primary stage and opens the initial view (LOGIN).
     *
     * @param primaryStage the primary stage of the JavaFX application
     * @throws RemoteException if a remote exception occurs while opening the initial view
     */
    public void start(Stage primaryStage) throws RemoteException {
        this.primaryStage = primaryStage;
        openView(ViewFactory.LOGIN);
    }

    /**
     * Opens the view with the specified identifier.
     *
     * This method loads the root Region of the view using the ViewFactory based on the given identifier,
     * sets it as the root of the current scene, updates the title of the primary stage,
     * resizes the stage to fit the content, and displays the stage.
     *
     * @param id the identifier of the view to open
     * @throws RemoteException if a remote exception occurs while loading the view
     */
    public void openView(String id) throws RemoteException {
        Region root = viewFactory.load(id);
        currentScene.setRoot(root);
        if (root.getUserData() == null) {
            primaryStage.setTitle("");
        } else {
            primaryStage.setTitle(root.getUserData().toString());
        }
        primaryStage.setScene(currentScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Closes the currently open view by closing the primary stage.
     */
    public void closeView() {
        primaryStage.close();
    }
}
