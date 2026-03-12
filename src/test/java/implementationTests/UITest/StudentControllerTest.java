package implementationTests.UITest;

import controller.StudentController;
import javafx.scene.Node;
import model.dao.implementation.*;
import model.entity.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;

public class StudentControllerTest extends ApplicationTest {

    private static Integer testUserTeacherId;
    private static Integer testTeacherId;
    private static Integer testCourseId;
    private static Integer testAssignmentId;
    private static Integer testUserStudentId;
    private static Integer testStudentId;
    private static Integer testEnrollmentId;
    private static Integer testGradeId;

    @BeforeAll
    static void setupData() {
        UserDaoImpl userDao = new UserDaoImpl();
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        CourseDaoImpl courseDao = new CourseDaoImpl();
        AssignmentDaoImpl assignmentDao = new AssignmentDaoImpl();
        StudentDaoImpl studentDao = new StudentDaoImpl();
        EnrollmentDaoImpl enrollmentDao = new EnrollmentDaoImpl();
        GradeDaoImpl gradeDao = new GradeDaoImpl();

        User teacherUser = new User();
        teacherUser.setUsername("uistudentteacher");
        teacherUser.setPasswordHash("password");
        teacherUser.setRole("TEACHER");
        userDao.create(teacherUser);
        testUserTeacherId = teacherUser.getId();

        Teacher teacher = new Teacher();
        teacher.setFirstName("UI");
        teacher.setLastName("StudentTeacher");
        teacher.setUser(teacherUser);
        teacherDao.create(teacher);
        testTeacherId = teacher.getId();

        Course course = new Course();
        course.setCode("STU999");
        course.setName("UI Student Test Course");
        course.setTeacher(teacher);
        courseDao.create(course);
        testCourseId = course.getId();

        Assignment assignment = new Assignment();
        assignment.setTitle("Student Test Assignment");
        assignment.setMaxScore(10);
        assignment.setType("Homework");
        assignment.setWeight(100.0f);
        assignment.setCourse(course);
        assignmentDao.create(assignment);
        testAssignmentId = assignment.getId();

        User studentUser = new User();
        studentUser.setUsername("uistudenttest");
        studentUser.setPasswordHash("password");
        studentUser.setRole("STUDENT");
        userDao.create(studentUser);
        testUserStudentId = studentUser.getId();

        Student student = new Student();
        student.setFirstName("UI");
        student.setLastName("StudentTest");
        student.setEnrollmentYear(2025);
        student.setUser(studentUser);
        studentDao.create(student);
        testStudentId = student.getId();

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setStartDate(LocalDate.now());
        enrollment.setEndDate(LocalDate.now().plusMonths(3));
        enrollmentDao.create(enrollment);
        testEnrollmentId = enrollment.getId();

        Grade grade = new Grade();
        grade.setAssignment(assignment);
        grade.setStudent(student);
        grade.setScore(8);
        grade.setDate(LocalDate.now());
        gradeDao.create(grade);
        testGradeId = grade.getId();
    }

    @AfterAll
    static void tearDownData() {
        if (testGradeId != null) new GradeDaoImpl().delete(testGradeId);
        if (testEnrollmentId != null) new EnrollmentDaoImpl().delete(testEnrollmentId);
        if (testStudentId != null) new StudentDaoImpl().delete(testStudentId);
        if (testUserStudentId != null) new UserDaoImpl().delete(testUserStudentId);
        if (testAssignmentId != null) new AssignmentDaoImpl().delete(testAssignmentId);
        if (testCourseId != null) new CourseDaoImpl().delete(testCourseId);
        if (testTeacherId != null) new TeacherDaoImpl().delete(testTeacherId);
        if (testUserTeacherId != null) new UserDaoImpl().delete(testUserTeacherId);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();

        StudentController controller = loader.getController();
        Student student = new StudentDaoImpl().findById(testStudentId);
        Course course = new CourseDaoImpl().findById(testCourseId);
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