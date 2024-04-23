package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.DonateViewModel;
import sep.viewmodel.HelpViewModel;

public class HelpViewController {
    private ViewHandler viewHandler;
    private HelpViewModel viewModel;
    private Region root;
    @FXML
    private Button backButton;
    @FXML private Button UserGuideButton;
    public void init(ViewHandler viewHandler, HelpViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }
    @FXML
    private void backButtonClicked(){
        viewHandler.openView("login"); //TODO: Change to main page view
    }
    @FXML private void UserGuideButtonClicked(){
        viewHandler.openView("UserGuide"); //TODO: Implement actual user guide
    }
    public void reset(){
        // TODO: reset view
    }
    public Region getRoot(){
        return root;
    }
}