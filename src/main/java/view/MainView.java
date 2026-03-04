package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.LoginController;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        loginStage.setTitle("Login");
        loginStage.showAndWait();

        LoginController loginController = loginLoader.getController();
        if (!loginController.isLoginSuccess()) {
            return;
        }

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Parent root = mainLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }
}