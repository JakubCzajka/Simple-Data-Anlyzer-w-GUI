module pl.polsl {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.polsl to javafx.fxml;
    exports pl.polsl;
    exports pl.polsl.controller;
    opens pl.polsl.controller to javafx.fxml;
}