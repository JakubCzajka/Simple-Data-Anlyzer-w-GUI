module pl.polsl {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.params;


    opens pl.polsl to javafx.fxml;
    exports pl.polsl;
    exports pl.polsl.controller;
    opens pl.polsl.controller to javafx.fxml;
}