package controller;

import javafx.application.Platform;
import model.entity.Course;
import model.entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentControllerTest {

    private StudentController controller;

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (Exception e) {
            //
        }
    }

    @BeforeEach
    void setUp() {
        controller = new StudentController();

        // Assign student
        Student student = new Student();
        student.setId(0);
        student.setFirstName("Firstname");
        student.setLastName("Lastname");
        student.setEnrollmentYear(2026);
        controller.setStudent(student);

        // Assign course
        Course course = new Course();
        course.setId(0);
        course.setCode("123");
        course.setName("Test course");
        controller.setCourse(course);

        // Mock UI components?
    }

    @Test
    void getAssignmentGradesTest() {
        Map<String, Integer> grades = controller.getAssignmentGrades();
        assertNotNull(grades);
    }

    @Test
    void calculateWeightedAverageTest() {
        double result =  controller.calculateWeightedAverage();
        assertNotNull(result);
    }
}
