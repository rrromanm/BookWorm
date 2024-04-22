package sep.view;

import sep.viewmodel.CreateAccountViewModel;
import javafx.scene.layout.Region;

public class CreateAccountViewController {
    private ViewHandler viewHandler;
    private CreateAccountViewModel viewModel;
    private Region root;

    public void init(ViewHandler viewHandler, CreateAccountViewModel viewModel, Region root)
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
