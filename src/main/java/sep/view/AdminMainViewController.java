package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import sep.viewmodel.AdminLoginViewModel;
import sep.viewmodel.AdminMainViewModel;

public class AdminMainViewController {
    private ViewHandler viewHandler;
    private AdminMainViewModel viewModel;
    private Region root;

    @FXML
    private Button backButton;

    public void init(ViewHandler viewHandler, AdminMainViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    @FXML
    private void backButtonClicked()
    {
        viewHandler.openView("login");
    }

    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
