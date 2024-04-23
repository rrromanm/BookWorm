package sep.view;

import sep.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;

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

    }

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
            }
        }
        mainViewController.reset();
        return mainViewController.getRoot();
    }

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

    public Region loadAdminLoginView() {
        if (adminLoginViewController == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("sep/AdminLoginView.fxml"));
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
            }
        }
        donateViewController.reset();
        return donateViewController.getRoot();
    }
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
    public Region loadProfileView(){
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
    public Region loadMyBooksView(){ // controller not implemented yet
        if(myBooksViewController == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sep/MyBooksView.fxml"));
            try {
                Region root = loader.load();
                myBooksViewController = loader.getController();
                myBooksViewController.init(viewHandler, viewModelFactory.getProfileViewModel(), root);
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        myBooksViewController.reset();
        return myBooksViewController.getRoot();
    }

    public Region load(String id) {
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
            default -> throw new IllegalArgumentException("Unknown view: " + id);
        };
        return root;
    }
}