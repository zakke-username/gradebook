package implementationTests.UITest;

import model.dao.implementation.UserDaoImpl;
import model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class LoginControllerTest extends ApplicationTest {

    private static Integer testUserId;

    @BeforeAll
    static void setupData() {
        UserDaoImpl userDao = new UserDaoImpl();

        User user = new User();
        user.setUsername("uitestlogin");
        user.setPasswordHash("password");
        user.setRole("TEACHER");
        userDao.create(user);
        testUserId = user.getId();
    }

    @AfterAll
    static void tearDownData() {
        if (testUserId != null) new UserDaoImpl().delete(testUserId);
    }

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
        clickOn("#usernameField").write("uitestlogin");
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