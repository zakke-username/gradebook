package UITest;

import controller.AssignmentController;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import model.dao.implementation.AssignmentDaoImpl;
import model.entity.Assignment;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class AssignmentControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssignmentView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        AssignmentController controller = loader.getController();
        Assignment assignment = new AssignmentDaoImpl().findById(1); // use a real id
        controller.setAssignment(assignment);
    }

    @Test
    void testTitleFieldIsPopulated() {
        TextField titleField = lookup("#titleTextField").query();
        assertFalse(titleField.getText().isEmpty());
    }

    @Test
    void testPointsFieldIsPopulated() {
        TextField pointsField = lookup("#pointsTextField").query();
        assertFalse(pointsField.getText().isEmpty());
    }

    @Test
    void testSaveButtonIsVisible() {
        verifyThat("#saveButton", Node::isVisible);
    }

    @Test
    void testSaveUpdatesAssignment() {
        clickOn("#titleTextField").eraseText(20).write("Updated Title");
        clickOn("#saveButton");
        Assignment updated = new AssignmentDaoImpl().findById(1);
        assertEquals("Updated Title", updated.getTitle());
    }
}