module com.example.loginsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.loginsystem to javafx.fxml;
    exports com.example.loginsystem;
}