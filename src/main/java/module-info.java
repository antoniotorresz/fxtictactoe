module com.antonio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.sql;

    opens com.antonio.controller to javafx.fxml;
    opens com.antonio.model to javafx.base;
    opens com.antonio.repository to java.sql;
    exports com.antonio;
}
