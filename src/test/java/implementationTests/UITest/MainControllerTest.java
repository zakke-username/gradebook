package implementationTests.UITest;

import controller.MainController;
import javafx.scene.control.ListView;
import model.dao.implementation.*;
import model.entity.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;

public class MainControllerTest extends ApplicationTest {

    private static Integer testUserId;
    private static Integer testTeacherId;
    private static Integer testCourseId;
    private static Integer testAssignmentId;

    @BeforeAll
    static void setupData() {
        UserDaoImpl userDao = new UserDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        CourseDaoImpl courseDao = new CourseDaoImpl();
        AssignmentDaoImpl assignmentDao = new AssignmentDaoImpl();

        User user = new User();
        user.setUsername("uimaintestteacher");
        user.setPasswordHash("password");
        user.setRole("TEACHER");
        userDao.create(user);
        testUserId = user.getId();

        Teacher teacher = new Teacher();
        teacher.setFirstName("UI");
        teacher.setLastName("MainTester");
        teacher.setUser(user);
        teacherDao.create(teacher);
        testTeacherId = teacher.getId();

        Course course = new Course();
        course.setCode("MAIN999");
        course.setName("UI Main Test Course");
        course.setTeacher(teacher);
        courseDao.create(course);
        testCourseId = course.getId();

        Assignment assignment = new Assignment();
        assignment.setTitle("Main Test Assignment");
        assignment.setMaxScore(10);
        assignment.setType("Homework");
        assignment.setWeight(10.0f);
        assignment.setCourse(course);
        assignmentDao.create(assignment);
        testAssignmentId = assignment.getId();
    }

    @AfterAll
    static void tearDownData() {
        if (testAssignmentId != null) new AssignmentDaoImpl().delete(testAssignmentId);
        if (testCourseId != null) new CourseDaoImpl().delete(testCourseId);
        if (testTeacherId != null) new TeacherDaoImpl().delete(testTeacherId);
        if (testUserId != null) new UserDaoImpl().delete(testUserId);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        MainController controller = loader.getController();
        User user = new UserDaoImpl().findById(testUserId);
        controller.setUser(user);
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