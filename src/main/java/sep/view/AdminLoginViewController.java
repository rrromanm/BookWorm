package sep.view;

import sep.viewmodel.AdminLoginViewModel;
import javafx.scene.layout.Region;

public class AdminLoginViewController {
    private ViewHandler viewHandler;
    private AdminLoginViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, AdminLoginViewModel viewModel, Region root)
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
