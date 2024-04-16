module com.example.sep2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sep2 to javafx.fxml;
    exports com.example.sep2;
}