package sep.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sep.viewmodel.AdminLoginViewModel;
import javafx.scene.layout.Region;

public class AdminLoginViewController {
    private ViewHandler viewHandler;
    private AdminLoginViewModel viewModel;
    private Region root;

    @FXML private Button backButton;

    public void init(ViewHandler viewHandler, AdminLoginViewModel viewModel, Region root)
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
