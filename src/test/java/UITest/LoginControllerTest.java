package UITest;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class LoginControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @Test
    void testFailedLogin() {
        clickOn("#usernameField").write("xd");
        clickOn("#passwordField").write("xd");
        clickOn("Kirjaudu");

        verifyThat("#errorLabel", hasText("Kirjautuminen epäonnistui!"));
    }

    @Test
    void testEmptyLogin() {
        clickOn("Kirjaudu");
        verifyThat("#errorLabel", hasText("Kirjautuminen epäonnistui!"));
    }

    @Test
    void testSuccessfulLogin() {
        clickOn("#usernameField").write("johndoe");
        clickOn("#passwordField").write("password");
        clickOn("Kirjaudu");


        Stage loginStage = listWindows().stream()
                .filter(w -> w instanceof Stage)
                .map(w -> (Stage) w)
                .filter(s -> s.getTitle().equals("Login"))
                .findFirst()
                .orElse(null);

        assert loginStage == null || !loginStage.isShowing();
    }
}