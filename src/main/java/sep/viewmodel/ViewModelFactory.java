package sep.viewmodel;

import sep.model.Model;

public class ViewModelFactory
{
    private final LoginViewModel loginViewModel;
    private final AdminLoginViewModel adminLoginViewModel;
    private final CreateAccountViewModel createAccountViewModel;
    private final MainViewModel mainViewModel;

    public ViewModelFactory(Model model) {
        this.loginViewModel = new LoginViewModel(model);
        this.adminLoginViewModel = new AdminLoginViewModel(model);
        this.createAccountViewModel = new CreateAccountViewModel(model);
        this.mainViewModel = new MainViewModel(model);
    }

    public LoginViewModel getLoginViewModel()
    {
        return loginViewModel;
    }

    public AdminLoginViewModel getAdminLoginViewModel()
    {
        return adminLoginViewModel;
    }

    public CreateAccountViewModel getCreateAccountViewModel()
    {
        return createAccountViewModel;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }
}
