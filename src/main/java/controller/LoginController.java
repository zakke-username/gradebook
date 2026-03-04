package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("asd".equals(username) && "123".equals(password)) {
            System.out.println("Kirjautuminen onnistui!");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Kirjautuminen epäonnistui!");
            if (errorLabel != null) {
                errorLabel.setText("Virheellinen käyttäjänimi tai salasana!");
            }
        }
    }
}