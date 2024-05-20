package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminServerLogViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

public class AdminServerLogViewController {
    private ViewHandler viewHandler;
    private AdminServerLogViewModel viewModel;
    private Region root;
    private final File file = new File("src/main/java/sep/file/LibraryLog");
    @FXML
    private Button backButton;
    @FXML
    private TextArea logTextArea;

    public void init(ViewHandler viewHandler, AdminServerLogViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        initializeArea();
    }

    public void initializeArea() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                logTextArea.setText(content.toString());
            } catch (IOException e) {
                logTextArea.setText("Failed to load log file: " + e.getMessage());
            }
        } else {
            logTextArea.setText("Log file not found.");
        }
    }

    @FXML
    private void backButtonClicked() throws RemoteException {
        viewHandler.openView("adminMainView");
    }

    public void reset() {
        // TODO: reset view
    }

    public Region getRoot() {
        return root;
    }
}
