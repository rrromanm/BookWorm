package sep.view;

import sep.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * The ViewFactory class is responsible for loading different views of the application.
 * It uses JavaFX FXMLLoader to load FXML files and initialize corresponding controllers.
 *
 * @author Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class ViewFactory
{
    public static final String ADMINLOGIN = "adminLogin";
    public static final String LOGIN = "login";
    public static final String CREATEACCOUNT = "createAccount";
    public static final String DONATEBOOK = "donateBook";
    public static final String ADMINMANAGEACCOUNTS = "adminManageAccounts";
    public static final String ADMINSERVERLOG = "adminServerLog";
    public static final String HELP = "help";
    public static final String USERMAIN = "userMain";
    public static final String PROFILE = "profile";
    public static final String MYBOOKS = "myBooks";
    public static final String ADMINMANAGEEVENTS = "adminManageEvents";
    public static final String ADMINMANAGEDONATEDBOOKS = "adminManageDonatedBooks";
    public static final String ADMINMAINVIEW = "adminMainView";
    public static final String ADMINMANAGEBOOKSVIEW = "adminManageBooksView";
    public static final String EVENTSVIEW = "eventsView";


    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private AdminLoginViewController adminLoginViewController;
    private LoginViewController loginViewController;
    private CreateAccountViewController createAccountViewController;
    private DonateViewController donateViewController;
    private AdminManageAccountsViewController adminManageAccountsViewController;
    private AdminServerLogViewController adminServerLogViewController;
    private HelpViewController helpViewController;
    private MainViewController mainViewController;
    private ProfileViewController profileViewController;
    private MyBooksViewController myBooksViewController;
    private AdminManageEventsViewController adminManageEventsViewController;
    private AdminManageDonatedBooksViewController adminManageDonatedBooksViewController;
    private AdminMainViewController adminMainViewController;
    private AdminManageBooksViewController adminManageBooksViewController;
    private EventsViewController eventsViewController;

    /**
     * Constructs a new ViewFactory object with the specified ViewHandler and ViewModelFactory.
     *
     * @param viewHandler       the ViewHandler responsible for managing views
     * @param viewModelFactory  the ViewModelFactory responsible for creating view models
     */
    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.adminLoginViewController = null;
        this.loginViewController = null;
        this.createAccountViewController = null;
        this.donateViewController = null;
        this.adminManageAccountsViewController = null;
        this.adminServerLogViewController = null;
        this.helpViewController = null;
        this.mainViewController = null;
        this.profileViewController = null;
        this.myBooksViewController = null;
        this.adminManageEventsViewController = null;
        this.adminManageDonatedBooksViewController = null;
        this.adminMainViewController = null;
        this.adminManageBooksViewController = null;
        this.eventsViewController = null;
    }

    /**
     * Loads the main view of the application.
     *
     * If the main view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the main view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadMainView() {
        if (mainViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/MainView.fxml"));
            try {
                Region root = loader.load();
                mainViewController = loader.getController();
                mainViewController.init(viewHandler, viewModelFactory.getMainViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        mainViewController.reset();
        return mainViewController.getRoot();
    }

    /**
     * Loads the login view of the application.
     *
     * If the login view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the login view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadLoginView() {
        if (loginViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/LoginView.fxml"));
            try {
                Region root = loader.load();
                loginViewController = loader.getController();
                loginViewController.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        loginViewController.reset();
        return loginViewController.getRoot();
    }

    /**
     * Loads the admin login view of the application.
     *
     * If the admin login view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin login view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminLoginView() {
        if (adminLoginViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/AdminLoginView.fxml"));
            try {
                Region root = loader.load();
                adminLoginViewController = loader.getController();
                adminLoginViewController.init(viewHandler, viewModelFactory.getAdminLoginViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminLoginViewController.reset();
        return adminLoginViewController.getRoot();
    }

    /**
     * Loads the create account view of the application.
     *
     * If the create account view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the create account view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadCreateAccountView() {
        if (createAccountViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/CreateAccountView.fxml"));
            try {
                Region root = loader.load();
                createAccountViewController = loader.getController();
                createAccountViewController.init(viewHandler, viewModelFactory.getCreateAccountViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        createAccountViewController.reset();
        return createAccountViewController.getRoot();
    }

    /**
     * Loads the donate view of the application.
     *
     * If the donate view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the donate view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadDonateView() {
        if (donateViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/DonateView.fxml"));
            try {
                Region root = loader.load();
                donateViewController = loader.getController();
                donateViewController.init(viewHandler, viewModelFactory.getDonateViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        donateViewController.reset();
        return donateViewController.getRoot();
    }

    /**
     * Loads the admin server log view of the application.
     *
     * If the admin server log view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin server log view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminServerLogView(){
        if(adminServerLogViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/ServerLogView.fxml"));
            try {
                Region root = loader.load();
                adminServerLogViewController = loader.getController();
                adminServerLogViewController.init(viewHandler, viewModelFactory.getAdminServerLogViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminServerLogViewController.reset();
        return adminServerLogViewController.getRoot();
    }

    /**
     * Loads the admin manage accounts view of the application.
     *
     * If the admin manage accounts view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin manage accounts view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminManageAccountsView(){
        if(adminManageAccountsViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/ManageAccountsView.fxml"));
            try {
                Region root = loader.load();
                adminManageAccountsViewController = loader.getController();
                adminManageAccountsViewController.init(viewHandler, viewModelFactory.getAdminManageAccountsViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminManageAccountsViewController.reset();
        return adminManageAccountsViewController.getRoot();
    }

    /**
     * Loads the help view of the application.
     *
     * If the help view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the help view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadHelpView(){
        if(helpViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/HelpView.fxml"));
            try {
                Region root = loader.load();
                helpViewController = loader.getController();
                helpViewController.init(viewHandler, viewModelFactory.getHelpViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        helpViewController.reset();
        return helpViewController.getRoot();
    }

    /**
     * Loads the profile view of the application.
     *
     * If the profile view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the profile view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadProfileView() throws RemoteException
    {
        if(profileViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/ProfileView.fxml"));
            try {
                Region root = loader.load();
                profileViewController = loader.getController();
                profileViewController.init(viewHandler, viewModelFactory.getProfileViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        profileViewController.reset();
        return profileViewController.getRoot();
    }

    /**
     * Loads the my books view of the application.
     *
     * If the my books view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the my books view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadMyBooksView(){
        if(myBooksViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/MyBooks.fxml"));
            try {
                Region root = loader.load();
                myBooksViewController = loader.getController();
                myBooksViewController.init(viewHandler, viewModelFactory.getMyBooksViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        myBooksViewController.reset();
        return myBooksViewController.getRoot();
    }

    /**
     * Loads the admin manage events view of the application.
     *
     * If the admin manage events view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin manage events view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminManageEventsView(){
        if(adminManageEventsViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/AdminManageEventsView.fxml"));
            try{
                Region root = loader.load();
                adminManageEventsViewController = loader.getController();
                adminManageEventsViewController.init(viewHandler, viewModelFactory.getAdminManageEventsViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminManageEventsViewController.reset();
        return adminManageEventsViewController.getRoot();
    }

    /**
     * Loads the admin donated books view of the application.
     *
     * If the admin donated books view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin donated books view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminDonatedBooksView(){
        if(adminManageDonatedBooksViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/ManageDonatedBooksView.fxml"));
            try{
                Region root = loader.load();
                adminManageDonatedBooksViewController = loader.getController();
                adminManageDonatedBooksViewController.init(viewHandler, viewModelFactory.getAdminManageDonatedBooksViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminManageDonatedBooksViewController.reset();
        return adminManageDonatedBooksViewController.getRoot();
    }

    /**
     * Loads the admin main view of the application.
     *
     * If the admin main view controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin main view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminMainView(){
        if(adminMainViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/AdminMainView.fxml"));
            try{
                Region root = loader.load();
                adminMainViewController = loader.getController();
                adminMainViewController.init(viewHandler, viewModelFactory.getAdminMainViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        adminMainViewController.reset();
        return adminMainViewController.getRoot();
    }

    /**
     * Loads the admin manage books view of the application.
     *
     * If the admin manage books view  controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the admin manage books view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadAdminManageBooksView(){
        if(adminManageBooksViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/AdminManageBooksView.fxml"));
            try{
                Region root = loader.load();
                adminManageBooksViewController = loader.getController();
                adminManageBooksViewController.init(viewHandler, viewModelFactory.getAdminManageBooksViewModel(), root);
            } catch (IOException | SQLException e) {
                throw new IOError(e);
            }
        }
        adminManageBooksViewController.reset();
        return adminManageBooksViewController.getRoot();
    }

    /**
     * Loads the events view of the application.
     *
     * If the events view  controller is not initialized, it loads the FXML file,
     * initializes the controller, and sets up the necessary dependencies.
     * Then it resets the controller and returns the root Region of the view.
     *
     * @return the root Region of the events view
     * @throws IOError if an I/O error occurs while loading the FXML file
     * @throws RuntimeException if a SQL exception occurs while initializing the controller
     */
    public Region loadEventsView(){
        if(eventsViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/EventView.fxml"));
            try{
                Region root = loader.load();
                eventsViewController = loader.getController();
                eventsViewController.init(viewHandler, viewModelFactory.getEventsViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        eventsViewController.reset();
        return eventsViewController.getRoot();
    }


    /**
     * Loads the specified view based on the provided identifier.
     *
     * This method delegates the loading of different views based on the given identifier.
     * The identifier determines which view to load by switching between different cases
     * and invoking the corresponding load methods for each view. If the provided identifier
     * does not match any known views, it throws an IllegalArgumentException.
     *
     * @param id the identifier of the view to load
     * @return the root Region of the loaded view
     * @throws RemoteException if a remote exception occurs while loading the view
     * @throws IllegalArgumentException if the provided identifier does not match any known views
     */
    public Region load(String id) throws RemoteException
    {
        Region root = switch(id) {
            case LOGIN -> loadLoginView();
            case ADMINLOGIN -> loadAdminLoginView();
            case CREATEACCOUNT -> loadCreateAccountView();
            case DONATEBOOK -> loadDonateView();
            case ADMINMANAGEACCOUNTS -> loadAdminManageAccountsView();
            case ADMINSERVERLOG -> loadAdminServerLogView();
            case HELP -> loadHelpView();
            case USERMAIN -> loadMainView();
            case PROFILE -> loadProfileView();
            case MYBOOKS -> loadMyBooksView();
            case ADMINMANAGEEVENTS -> loadAdminManageEventsView();
            case ADMINMANAGEDONATEDBOOKS  -> loadAdminDonatedBooksView();
            case ADMINMAINVIEW -> loadAdminMainView();
            case ADMINMANAGEBOOKSVIEW -> loadAdminManageBooksView();
            case EVENTSVIEW -> loadEventsView();
            default -> throw new IllegalArgumentException("Unknown view: " + id);
        };
        return root;
    }
}
