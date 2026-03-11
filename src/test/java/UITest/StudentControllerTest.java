package UITest;

import controller.StudentController;
import javafx.scene.Node;
import model.dao.implementation.CourseDaoImpl;
import model.dao.implementation.StudentDaoImpl;
import model.entity.Course;
import model.entity.Student;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

public class StudentControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        StudentController controller = loader.getController();
        Student student = new StudentDaoImpl().findById(1); // use a real id
        Course course = new CourseDaoImpl().findById(1);
        controller.setStudent(student);
        controller.setCourse(course);
        controller.displayInfo();
    }

    @Test
    void testStudentNameLabelIsSet() {
        Label label = lookup("#studentNameLabel").query();
        assertFalse(label.getText().isEmpty());
    }

    @Test
    void testCourseNameLabelIsSet() {
        Label label = lookup("#courseNameLabel").query();
        assertFalse(label.getText().isEmpty());
    }

    @Test
    void testGradeTableIsVisible() {
        verifyThat("#gradeTable", Node::isVisible);
    }

    @Test
    void testWeightedAverageLabelIsVisible() {
        verifyThat("#weightedAverageLabel", Node::isVisible);
    }
}

