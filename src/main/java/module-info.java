module com.example.sep2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;


    opens sep to javafx.fxml;
    exports sep;
}