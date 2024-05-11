module sep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires remoteobserver;


    opens sep to javafx.fxml;
    opens sep.view to javafx.fxml;
    opens sep.model to javafx.base;

    exports sep;
    exports sep.view;
    exports sep.shared to java.rmi;
    exports sep.model;
}