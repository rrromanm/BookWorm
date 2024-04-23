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
    public static final String USERMAIN = "userMain";

    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;
    private AdminLoginViewController adminLoginViewController;
    private LoginViewController loginViewController;
    private CreateAccountViewController createAccountViewController;
    private MainViewController mainViewController;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.adminLoginViewController = null;
        this.loginViewController = null;
        this.createAccountViewController = null;
        this.mainViewController = null;
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

    public Region load(String id) {
        Region root = switch(id) {
            case LOGIN -> loadLoginView();
            case ADMINLOGIN -> loadAdminLoginView();
            case CREATEACCOUNT -> loadCreateAccountView();
            case USERMAIN -> loadMainView();
            default -> throw new IllegalArgumentException("Unknown view: " + id);
        };
        return root;
    }
}
