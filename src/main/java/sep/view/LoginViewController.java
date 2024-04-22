package sep.view;

import sep.viewmodel.LoginViewModel;
import javafx.scene.layout.Region;


public class LoginViewController {
    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
    }

    public void reset()
    {

    }

    public Region getRoot()
    {
        return root;
    }
}
