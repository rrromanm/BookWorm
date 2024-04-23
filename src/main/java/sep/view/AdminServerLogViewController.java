package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminServerLogViewModel;
import sep.viewmodel.DonateViewModel;

public class AdminServerLogViewController {
    private ViewHandler viewHandler;
    private AdminServerLogViewModel viewModel;
    private Region root;
    @FXML
    private Button backButton;
    public void init(ViewHandler viewHandler, AdminServerLogViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }
    @FXML
    private void backButtonClicked(){
        viewHandler.openView("login"); //TODO: Change it to main view page
    }
    public void reset(){
        // TODO: reset view
    }
    public Region getRoot(){
        return root;
    }

}
