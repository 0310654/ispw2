module com.example.ispw2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.google.gson;
    requires java.sql;

    opens com.example.ispw2 to javafx.fxml;
    opens com.example.ispw2.view.gui.controller to javafx.fxml;
    exports com.example.ispw2;

    opens com.example.ispw2.altro.Wrapper to com.google.gson; // per ClientiWrapper
    opens com.example.ispw2.view.gui.other to javafx.fxml;

}