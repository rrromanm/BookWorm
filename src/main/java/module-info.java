module sep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens sep to javafx.fxml;
    opens sep.view to javafx.fxml;

    exports sep;
    exports sep.view;
}