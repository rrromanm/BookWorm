module sep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;
    requires remoteobserver;
//    requires javafx.swing;


    opens sep.server to javafx.fxml;
    opens sep.view to javafx.fxml;
    opens sep.client to javafx.fxml;
    opens sep.model to javafx.base;

    exports sep;
    exports sep.view;
    exports sep.viewmodel;
    exports sep.shared to java.rmi;
    exports sep.model;
    exports sep.client;
}