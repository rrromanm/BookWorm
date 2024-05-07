package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.CreateAccountViewModel;
import sep.viewmodel.DonateViewModel;

public class DonateViewController {
    private ViewHandler viewHandler;
    private DonateViewModel viewModel;
    private Region root;
    @FXML private Button backButton;
    @FXML private Button SubmitButton;
    public void init(ViewHandler viewHandler, DonateViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }
    @FXML
    private void backButtonClicked(){
        viewHandler.openView(ViewFactory.USERMAIN);
    }
    @FXML private void submitButtonClicked(){
        //TODO: Implement working submit
    }
    public void reset(){
        // TODO: reset view
    }
    public Region getRoot(){
        return root;
    }
}
