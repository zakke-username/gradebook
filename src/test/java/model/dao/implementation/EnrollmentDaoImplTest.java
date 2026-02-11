package model.dao.implementation;

import model.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentDaoImplTest extends AbstractDaoTest {

    private static Student testStudent;
    private static Teacher testTeacher;
    private static Course testCourse;

    @BeforeAll
    static void setup() {
        // Clean the database before tests run.
        wipeDatabase();

        // Create a teacher
        testTeacher = new Teacher();
        testTeacher.setFirstName("John");
        testTeacher.setLastName("Doe");
        teacherDao.create(testTeacher);

        // Create a course
        testCourse = new Course();
        testCourse.setName("Math 101");
        testCourse.setCode("MATH101");
        testCourse.setTeacher(testTeacher);
        courseDao.create(testCourse);

        // Create a student
        testStudent = new Student();
        testStudent.setFirstName("Alice");
        testStudent.setLastName("Smith");
        testStudent.setEnrollmentYear(2024);
        studentDao.create(testStudent);
    }

    @BeforeEach
    void wipeTable() {
        // Wipe the enrollment table before every test.
        List<Enrollment> enrollments = enrollmentDao.findAll();
        for (Enrollment e : enrollments) {
            enrollmentDao.delete(e.getId());
        }
    }

    @Test
    void createAndFindEnrollment() {
        Enrollment e = new Enrollment();
        e.setStudent(testStudent);
        e.setCourse(testCourse);
        e.setStartDate(LocalDate.of(2024, 2, 1));
        e.setEndDate(LocalDate.of(2024, 6, 1));
        e.setAttendance(95.5f);
        e.setFinalGrade(88);

        enrollmentDao.create(e);

        assertNotNull(e.getId());

        Enrollment found = enrollmentDao.findById(e.getId());
        assertEquals(testStudent.getId(), found.getStudent().getId());
        assertEquals(testCourse.getId(), found.getCourse().getId());
        assertEquals(95.5f, found.getAttendance());
        assertEquals(88, found.getFinalGrade());
    }

    @Test
    void findAllEnrollments() {
        Enrollment e1 = new Enrollment();
        e1.setStudent(testStudent);
        e1.setCourse(testCourse);
        e1.setStartDate(LocalDate.now());
        e1.setEndDate(LocalDate.now().plusMonths(4));
        enrollmentDao.create(e1);

        Enrollment e2 = new Enrollment();
        e2.setStudent(testStudent);
        e2.setCourse(testCourse);
        e2.setStartDate(LocalDate.now());
        e2.setEndDate(LocalDate.now().plusMonths(4));
        enrollmentDao.create(e2);

        List<Enrollment> all = enrollmentDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findByStudentId() {
        Enrollment e = new Enrollment();
        e.setStudent(testStudent);
        e.setCourse(testCourse);
        e.setStartDate(LocalDate.now());
        e.setEndDate(LocalDate.now().plusMonths(4));
        enrollmentDao.create(e);

        List<Enrollment> list = enrollmentDao.findByStudentId(testStudent.getId());
        assertEquals(1, list.size());
        assertEquals(testStudent.getId(), list.get(0).getStudent().getId());
    }

    @Test
    void findByCourseId() {
        Enrollment e = new Enrollment();
        e.setStudent(testStudent);
        e.setCourse(testCourse);
        e.setStartDate(LocalDate.now());
        e.setEndDate(LocalDate.now().plusMonths(4));
        enrollmentDao.create(e);

        List<Enrollment> list = enrollmentDao.findByCourseId(testCourse.getId());
        assertEquals(1, list.size());
        assertEquals(testCourse.getId(), list.get(0).getCourse().getId());
    }

    @Test
    void updateEnrollment() {
        Enrollment e = new Enrollment();
        e.setStudent(testStudent);
        e.setCourse(testCourse);
        e.setStartDate(LocalDate.now());
        e.setEndDate(LocalDate.now().plusMonths(4));
        e.setAttendance(90f);
        enrollmentDao.create(e);

        e.setAttendance(95f);
        enrollmentDao.update(e);

        Enrollment updated = enrollmentDao.findById(e.getId());
        assertEquals(95f, updated.getAttendance());
    }

    @Test
    void deleteEnrollment() {
        Enrollment e = new Enrollment();
        e.setStudent(testStudent);
        e.setCourse(testCourse);
        e.setStartDate(LocalDate.now());
        e.setEndDate(LocalDate.now().plusMonths(4));
        enrollmentDao.create(e);

        enrollmentDao.delete(e.getId());

        Enrollment deleted = enrollmentDao.findById(e.getId());
        assertNull(deleted);
    }
}