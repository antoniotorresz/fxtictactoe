module com.antonio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.antonio.controller to javafx.fxml;
    exports com.antonio;
}
