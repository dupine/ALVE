module com.alve.alve0 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.alve.alve0 to javafx.fxml;
    exports com.alve.alve0;
}