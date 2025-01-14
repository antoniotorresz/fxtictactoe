module com.antonio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.antonio.controller to javafx.fxml;
    opens com.antonio.model to javafx.base;
    exports com.antonio;
}
