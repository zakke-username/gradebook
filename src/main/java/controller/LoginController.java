package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.dao.UserDao;
import model.dao.implementation.UserDaoImpl;
import model.entity.User;

public class LoginController {

    private static User loggedInUser;
    private final UserDao userDao = new UserDaoImpl();
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    private boolean loginSuccess = false;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        User user;
        try {
            user = userDao.findByUsername(username);
        } catch (Exception e) {
            user = null;
        }

        if (user != null && checkPassword(password, user.getPasswordHash())) {
            loginSuccess = true;
            loggedInUser = user;
            System.out.println("Kirjautuminen onnistui!");
            // isTeacher = user.getRole().equalsIgnoreCase("TEACHER");
            // isStudent = user.getRole().equalsIgnoreCase("STUDENT");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
        } else {
            showError("Kirjautuminen epäonnistui!");
        }
    }

    private boolean checkPassword(String plainPassword, String storedHash) {
        return plainPassword.equals(storedHash);
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
        }
        System.out.println(message);
    }
}