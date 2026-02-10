import model.entity.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class EntityTest {


    @Test
    void teacherSettersAndGetters() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jouni");
        teacher.setLastName("Virtanen");
        teacher.setId(1);

        assertEquals("Jouni", teacher.getFirstName());
        assertEquals("Virtanen", teacher.getLastName());
        assertEquals(1, teacher.getId());
    }


    @Test
    void studentSettersAndGetters() {
        Student student = new Student();
        student.setFirstName("Jaakko");
        student.setLastName("Korhonen");
        student.setId(1);
        student.setEnrollmentYear(2025);

        assertEquals("Jaakko", student.getFirstName());
        assertEquals("Korhonen", student.getLastName());
        assertEquals(1, student.getId());
        assertEquals(2025, student.getEnrollmentYear());
    }

    @Test
    void courseSettersAndGetters() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Anna");
        teacher.setLastName("Lehtinen");

        Course course = new Course();
        course.setId(1);
        course.setName("Matikka");
        course.setCode("MATH_999");
        course.setTeacher(teacher);

        assertEquals(1, course.getId());
        assertEquals("Matikka", course.getName());
        assertEquals("MATH_999", course.getCode());
        assertEquals("Anna", course.getTeacher().getFirstName());
    }

    @Test
    void assignmentSettersAndGetters() {
        Course course = new Course();
        course.setName("Fysiikka");

        Assignment assignment = new Assignment();
        assignment.setId(1);
        assignment.setTitle("Sprint 2");
        assignment.setMaxScore(100);
        assignment.setWeight(0.2f);
        assignment.setType("Homework");
        assignment.setCourse(course);

        assertEquals(1, assignment.getId());
        assertEquals("Sprint 2", assignment.getTitle());
        assertEquals(100, assignment.getMaxScore());
        assertEquals(0.2f, assignment.getWeight(), 0.0001f);
        assertEquals("Homework", assignment.getType());
        assertEquals("Fysiikka", assignment.getCourse().getName());
    }

    @Test
    void enrollmentEntityWorks() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Eeva");

        Course course = new Course();
        course.setId(1);
        course.setName("Kemia");

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStartDate(LocalDate.of(2026, 2, 1));
        enrollment.setEndDate(LocalDate.of(2026, 6, 30));
        enrollment.setAttendance(95.5f);
        enrollment.setFinalGrade(5);

        assertEquals(1, enrollment.getId());
        assertEquals("Eeva", enrollment.getStudent().getFirstName());
        assertEquals("Kemia", enrollment.getCourse().getName());
        assertEquals(LocalDate.of(2026, 2, 1), enrollment.getStartDate());
        assertEquals(LocalDate.of(2026, 6, 30), enrollment.getEndDate());
        assertEquals(95.5f, enrollment.getAttendance(), 0.0001f);
        assertEquals(5, enrollment.getFinalGrade());
    }


    @Test
    void complexScenarioWorks() {
        // Teacher
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("Mikko");
        teacher.setLastName("Mäkinen");

        // Course
        Course course = new Course();
        course.setId(1);
        course.setName("Biologia");
        course.setCode("BIO_999");
        course.setTeacher(teacher);

        // Assignment
        Assignment assignment = new Assignment();
        assignment.setId(1);
        assignment.setTitle("Sprint 2");
        assignment.setMaxScore(50);
        assignment.setWeight(0.3f);
        assignment.setType("Projekti");
        assignment.setCourse(course);

        // Student
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Liisa");

        // Enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setId(1);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStartDate(LocalDate.of(2026, 2, 1));
        enrollment.setEndDate(LocalDate.of(2026, 6, 30));
        enrollment.setAttendance(100f);
        enrollment.setFinalGrade(5);

        // Assertions
        assertEquals("Mikko", course.getTeacher().getFirstName());
        assertEquals("Sprint 2", assignment.getTitle());
        assertEquals("Liisa", enrollment.getStudent().getFirstName());
        assertEquals(100f, enrollment.getAttendance(), 0.0001f);
        assertEquals(5, enrollment.getFinalGrade());
        assertEquals(course, assignment.getCourse());
        assertEquals(course, enrollment.getCourse());
    }
}
