module com.mycompany.booktrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.mycompany.booktrack to javafx.fxml;
    exports com.mycompany.booktrack;
}
