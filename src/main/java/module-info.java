module sep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens sep to javafx.fxml;
    opens sep.view to javafx.fxml;

    exports sep;
    exports sep.view;
}