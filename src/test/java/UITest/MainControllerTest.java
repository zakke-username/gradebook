package UITest;

import controller.MainController;
import javafx.scene.control.ListView;
import model.entity.User;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class MainControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("testuser");
    }

    @Test
    void testCourseListViewIsPopulated() {
        ListView<?> listView = lookup("#courseListView").query();
        assertFalse(listView.getItems().isEmpty());
    }

    @Test
    void testAssignmentListViewIsPopulated() {
        ListView<?> listView = lookup("#assignmentListView").query();
        assertFalse(listView.getItems().isEmpty());
    }

    @Test
    void testWelcomeLabelIsVisible() {
        verifyThat("#welcomeLabel", javafx.scene.Node::isVisible);
    }

    @Test
    void testCourseListViewIsVisible() {
        verifyThat("#courseListView", javafx.scene.Node::isVisible);
    }

    @Test
    void testAssignmentListViewIsVisible() {
        verifyThat("#assignmentListView", javafx.scene.Node::isVisible);
    }
}