package controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.entity.Assignment;
import model.entity.Course;
import model.entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CourseControllerTest {

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            //
        }
    }

    @Test
    void getStudentsTest() {
        CourseController controller = new CourseController();

        // Mock UI components
        controller.setCourseNameLabel(new Label());
        controller.setStudentListView(new ListView<>());
        controller.setAssignmentListView(new ListView<>());

        // Assign course to controller
        Course course = new Course();
        course.setId(0);
        course.setCode("123");
        course.setName("Test course");
        controller.setCourse(course);

        List<Student> students = controller.getStudents();
        assertNotNull(students);
    }

    @Test
    void getAssignmentsTest() {
        CourseController controller = new CourseController();

        // Mock UI components
        controller.setCourseNameLabel(new Label());
        controller.setStudentListView(new ListView<>());
        controller.setAssignmentListView(new ListView<>());

        // Assign course to controller
        Course course = new Course();
        course.setId(0);
        course.setCode("123");
        course.setName("Test course");
        controller.setCourse(course);

        List<Assignment> assignments = controller.getAssignments();
        assertNotNull(assignments);
    }
}
