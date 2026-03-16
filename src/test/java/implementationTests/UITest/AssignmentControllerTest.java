package implementationTests.UITest;

import controller.AssignmentController;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import model.dao.implementation.AssignmentDaoImpl;
import model.dao.implementation.CourseDaoImpl;
import model.dao.implementation.TeacherDaoImpl;
import model.dao.implementation.UserDaoImpl;
import model.entity.Assignment;
import model.entity.Course;
import model.entity.Teacher;
import model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;

public class AssignmentControllerTest extends ApplicationTest {

    private static AssignmentDaoImpl dao;
    private static Assignment testAssignment;
    private static Integer testUserId;
    private static Integer testTeacherId;
    private static Integer testCourseId;

    @BeforeAll
    static void setupData() {
        UserDaoImpl userDao = new UserDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        CourseDaoImpl courseDao = new CourseDaoImpl();
        dao = new AssignmentDaoImpl();

        User user = new User();
        user.setUsername("uitestteacher");
        user.setPasswordHash("password");
        user.setRole("TEACHER");
        userDao.create(user);
        testUserId = user.getId();

        Teacher teacher = new Teacher();
        teacher.setFirstName("UI");
        teacher.setLastName("Tester");
        teacher.setUser(user);
        teacherDao.create(teacher);
        testTeacherId = teacher.getId();

        Course course = new Course();
        course.setCode("UI999");
        course.setName("UI Test Course");
        course.setTeacher(teacher);
        courseDao.create(course);
        testCourseId = course.getId();

        testAssignment = new Assignment();
        testAssignment.setTitle("Sample Assignment");
        testAssignment.setMaxScore(10);
        testAssignment.setType("Homework");
        testAssignment.setWeight(10.0f);
        testAssignment.setCourse(course);
        dao.create(testAssignment);
    }

    @AfterAll
    static void tearDownData() {
        if (testAssignment != null) dao.delete(testAssignment.getId());
        if (testCourseId != null) new CourseDaoImpl().delete(testCourseId);
        if (testTeacherId != null) new TeacherDaoImpl().delete(testTeacherId);
        if (testUserId != null) new UserDaoImpl().delete(testUserId);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssignmentView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        AssignmentController controller = loader.getController();
        controller.setAssignment(testAssignment);
    }

    @Test
    void testTitleFieldIsPopulated() {
        TextField titleField = lookup("#titleTextField").query();
        assertFalse(titleField.getText().isEmpty());
        assertEquals("Updated Title", titleField.getText());
    }

    @Test
    void testPointsFieldIsPopulated() {
        TextField pointsField = lookup("#pointsTextField").query();
        assertFalse(pointsField.getText().isEmpty());
        assertEquals("10", pointsField.getText());
    }

    @Test
    void testSaveButtonIsVisible() {
        verifyThat("#saveButton", Node::isVisible);
    }

    @Test
    void testSaveUpdatesAssignment() {
        clickOn("#titleTextField").eraseText(50).write("Updated Title");
        clickOn("#saveButton");

        Assignment updated = dao.findById(testAssignment.getId());
        assertEquals("Updated Title", updated.getTitle());
    }
}