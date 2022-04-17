module com.example.loginsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.loginsystem to javafx.fxml;
    exports com.example.loginsystem;
    exports com.example.loginsystem.Database;
    opens com.example.loginsystem.Database to javafx.fxml;
}