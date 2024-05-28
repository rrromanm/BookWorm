package sep.viewmodel;

import sep.model.Model;
import sep.view.AdminServerLogViewController;

/**
 * The ViewModelFactory class is responsible for creating and providing instances of various view model classes.
 * Each view model corresponds to a specific view or functionality in the application.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class ViewModelFactory
{
    private final LoginViewModel loginViewModel;
    private final AdminLoginViewModel adminLoginViewModel;
    private final CreateAccountViewModel createAccountViewModel;
    private final DonateViewModel donateViewModel;
    private final AdminManageAccountsViewModel adminManageAccountsViewModel;
    private final AdminServerLogViewModel adminServerLogViewModel;
    private final HelpViewModel helpViewModel;
    private final MainViewModel mainViewModel;
    private final ProfileViewModel profileViewModel;
    private final MyBooksViewModel myBooksViewModel;
    private final AdminManageEventsViewModel adminManageEventsViewModel;
    private final AdminManageDonatedBooksViewModel adminManageDonatedBooksViewModel;
    private final AdminMainViewModel adminMainViewModel;
    private final AdminManageBooksViewModel adminManageBooksViewModel;
    private final EventsViewModel eventsViewModel;

    /**
     * Constructs a ViewModelFactory with the specified model.
     *
     * @param model The model to interact with for creating view models
     */
    public ViewModelFactory(Model model) {
        this.loginViewModel = new LoginViewModel(model);
        this.adminLoginViewModel = new AdminLoginViewModel(model);
        this.createAccountViewModel = new CreateAccountViewModel(model);
        this.donateViewModel = new DonateViewModel(model);
        this.adminManageAccountsViewModel = new AdminManageAccountsViewModel(model);
        this.adminServerLogViewModel = new AdminServerLogViewModel(model);
        this.helpViewModel = new HelpViewModel(model);
        this.mainViewModel = new MainViewModel(model);
        this.myBooksViewModel = new MyBooksViewModel(model);
        this.profileViewModel = new ProfileViewModel(model);
        this.adminManageEventsViewModel = new AdminManageEventsViewModel(model);
        this.adminManageDonatedBooksViewModel = new AdminManageDonatedBooksViewModel(model);
        this.adminMainViewModel = new AdminMainViewModel(model);
        this.adminManageBooksViewModel = new AdminManageBooksViewModel(model);
        this.eventsViewModel = new EventsViewModel(model);
    }

    /**
     * Retrieves the LoginViewModel instance.
     *
     * @return The LoginViewModel instance
     */
    public LoginViewModel getLoginViewModel()
    {
        return loginViewModel;
    }

    /**
     * Retrieves the AdminLoginViewModel instance.
     *
     * @return The AdminLoginViewModel instance
     */
    public AdminLoginViewModel getAdminLoginViewModel()
    {
        return adminLoginViewModel;
    }

    /**
     * Retrieves the CreateAccountViewModel instance.
     *
     * @return The CreateAccountViewModel instance
     */
    public CreateAccountViewModel getCreateAccountViewModel()
    {
        return createAccountViewModel;
    }

    /**
     * Retrieves the DonateViewModel instance.
     *
     * @return The DonateViewModel instance
     */
    public DonateViewModel getDonateViewModel(){return donateViewModel;}

    /**
     * Retrieves the AdminManageAccountsViewModel instance.
     *
     * @return The AdminManageAccountsViewModel instance
     */
    public AdminManageAccountsViewModel getAdminManageAccountsViewModel(){return adminManageAccountsViewModel;}

    /**
     * Retrieves the AdminServerLogViewModel instance.
     *
     * @return The AdminServerLogViewModel instance
     */
    public AdminServerLogViewModel getAdminServerLogViewModel() {return adminServerLogViewModel;}

    /**
     * Retrieves the HelpViewModel instance.
     *
     * @return The HelpViewModel instance
     */
    public HelpViewModel getHelpViewModel(){return helpViewModel;}

    /**
     * Retrieves the MainViewModel instance.
     *
     * @return The MainViewModel instance
     */
    public MainViewModel getMainViewModel(){return mainViewModel;}

    /**
     * Retrieves the MyBooksViewModel instance.
     *
     * @return The MyBooksViewModel instance
     */
    public MyBooksViewModel getMyBooksViewModel() {
        return myBooksViewModel;
    }

    /**
     * Retrieves the ProfileViewModel instance.
     *
     * @return The ProfileViewModel instance
     */
    public ProfileViewModel getProfileViewModel() {
        return profileViewModel;
    }

    /**
     * Retrieves the AdminManageEventsViewModel instance.
     *
     * @return The AdminManageEventsViewModel instance
     */
    public AdminManageEventsViewModel getAdminManageEventsViewModel() {
        return adminManageEventsViewModel;
    }

    /**
     * Retrieves the AdminManageDonatedBooksViewModel instance.
     *
     * @return The AdminManageDonatedBooksViewModel instance
     */
    public AdminManageDonatedBooksViewModel getAdminManageDonatedBooksViewModel() {
        return adminManageDonatedBooksViewModel;
    }

    /**
     * Retrieves the AdminMainViewModel instance.
     *
     * @return The AdminMainViewModel instance
     */
    public AdminMainViewModel getAdminMainViewModel() {
        return adminMainViewModel;
    }

    /**
     * Retrieves the AdminManageBooksViewModel instance.
     *
     * @return The AdminManageBooksViewModel instance
     */

    public AdminManageBooksViewModel getAdminManageBooksViewModel() {
        return adminManageBooksViewModel;
    }

    /**
     * Retrieves the EventsViewModel instance.
     *
     * @return The EventsViewModel instance
     */
    public EventsViewModel getEventsViewModel() {
        return eventsViewModel;
    }
}
