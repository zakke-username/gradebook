package UITest;

import controller.CourseController;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import model.dao.implementation.CourseDaoImpl;
import model.entity.Course;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class CourseControllerTest extends ApplicationTest {
    private CourseController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        controller = loader.getController();
        Course course = new CourseDaoImpl().findById(1);
        controller.setCourse(course);
    }

    @Test
    void testCourseNameLabelIsSet() {
        Label label = lookup("#courseNameLabel").query();
        assertFalse(label.getText().isEmpty());
    }

    @Test
    void testStudentListViewIsVisible() {
        verifyThat("#studentListView", Node::isVisible);
    }

    @Test
    void testAssignmentListViewIsVisible() {
        verifyThat("#assignmentListView", Node::isVisible);
    }

    @Test
    void testStudentListViewIsPopulated() {
        ListView<?> listView = lookup("#studentListView").query();
        assertFalse(listView.getItems().isEmpty());
    }
}