module org.schapm.savingscalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.schapm.savingscalculator to javafx.fxml;
    exports org.schapm.savingscalculator;
}