package sep.viewmodel;

import sep.model.Model;
import sep.view.AdminServerLogViewController;

public class ViewModelFactory
{
    private final LoginViewModel loginViewModel;
    private final AdminLoginViewModel adminLoginViewModel;
    private final CreateAccountViewModel createAccountViewModel;
    private final DonateViewModel donateViewModel;
    private final AdminManageAccountsViewModel adminManageAccountsViewModel;
    private final AdminServerLogViewModel adminServerLogViewModel;
    private final HelpViewModel helpViewModel;

    public ViewModelFactory(Model model) {
        this.loginViewModel = new LoginViewModel(model);
        this.adminLoginViewModel = new AdminLoginViewModel(model);
        this.createAccountViewModel = new CreateAccountViewModel(model);
        this.donateViewModel = new DonateViewModel(model);
        this.adminManageAccountsViewModel = new AdminManageAccountsViewModel(model);
        this.adminServerLogViewModel = new AdminServerLogViewModel(model);
        this.helpViewModel = new HelpViewModel(model);
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
    public DonateViewModel getDonateViewModel(){return donateViewModel;}
    public AdminManageAccountsViewModel getAdminManageAccountsViewModel(){return adminManageAccountsViewModel;}

    public AdminServerLogViewModel getAdminServerLogViewModel() {return adminServerLogViewModel;}
    public HelpViewModel getHelpViewModel(){return helpViewModel;}
}
