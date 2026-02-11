package model.dao.implementation;

import model.entity.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeDaoImplTest extends AbstractDaoTest{

    private static Student testStudent;
    private static Assignment testAssignment;

    @BeforeAll
    static void setup() {
        // Clean old data from database before tests are run.
        wipeDatabase();

        // Teacher
        Teacher teacher = new Teacher();
        teacher.setFirstName("Alan");
        teacher.setLastName("Turing");
        teacherDao.create(teacher);

        // Course
        Course course = new Course();
        course.setName("Computer Science");
        course.setCode("CS101");
        course.setTeacher(teacher);
        courseDao.create(course);

        // Student
        testStudent = new Student();
        testStudent.setFirstName("Grace");
        testStudent.setLastName("Hopper");
        testStudent.setEnrollmentYear(2024);
        studentDao.create(testStudent);

        // Assignment
        testAssignment = new Assignment();
        testAssignment.setTitle("Midterm");
        testAssignment.setType("Exam");
        testAssignment.setMaxScore(100);
        testAssignment.setWeight(0.4f);
        testAssignment.setCourse(course);
        assignmentDao.create(testAssignment);
    }

    @BeforeEach
    void wipeTable() {
        for (Grade g : gradeDao.findAll()) {
            gradeDao.delete(g.getId());
        }
    }

    @AfterAll
    static void cleanDatabase() {
        // Clean the database after all the tests have run.
        wipeDatabase();
    }

    @Test
    void createAndFindGrade() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(85);
        g.setDate(LocalDate.now());

        gradeDao.create(g);

        assertNotNull(g.getId());

        Grade found = gradeDao.findById(g.getId());
        assertEquals(85, found.getScore());
        assertEquals(testStudent.getId(), found.getStudent().getId());
        assertEquals(testAssignment.getId(), found.getAssignment().getId());
    }

    @Test
    void findAllGrades() {
        Grade g1 = new Grade();
        g1.setStudent(testStudent);
        g1.setAssignment(testAssignment);
        g1.setScore(90);
        g1.setDate(LocalDate.now());
        gradeDao.create(g1);

        Grade g2 = new Grade();
        g2.setStudent(testStudent);
        g2.setAssignment(testAssignment);
        g2.setScore(70);
        g2.setDate(LocalDate.now());
        gradeDao.create(g2);

        List<Grade> grades = gradeDao.findAll();
        assertEquals(2, grades.size());
    }

    @Test
    void findByStudentId() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(88);
        g.setDate(LocalDate.now());
        gradeDao.create(g);

        List<Grade> grades = gradeDao.findByStudentId(testStudent.getId());
        assertEquals(1, grades.size());
        assertEquals(testStudent.getId(), grades.get(0).getStudent().getId());
    }

    @Test
    void findByAssignmentId() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(92);
        g.setDate(LocalDate.now());
        gradeDao.create(g);

        List<Grade> grades = gradeDao.findByAssignmentId(testAssignment.getId());
        assertEquals(1, grades.size());
        assertEquals(testAssignment.getId(), grades.get(0).getAssignment().getId());
    }

    @Test
    void findByStudentAndAssignment() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(95);
        g.setDate(LocalDate.now());
        gradeDao.create(g);

        Grade found = gradeDao.findByStudentAndAssignment(
                testStudent.getId(),
                testAssignment.getId()
        );

        assertNotNull(found);
        assertEquals(95, found.getScore());
    }

    @Test
    void updateGrade() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(60);
        g.setDate(LocalDate.now());
        gradeDao.create(g);

        g.setScore(75);
        gradeDao.update(g);

        Grade updated = gradeDao.findById(g.getId());
        assertEquals(75, updated.getScore());
    }

    @Test
    void deleteGrade() {
        Grade g = new Grade();
        g.setStudent(testStudent);
        g.setAssignment(testAssignment);
        g.setScore(50);
        g.setDate(LocalDate.now());
        gradeDao.create(g);

        gradeDao.delete(g.getId());

        Grade deleted = gradeDao.findById(g.getId());
        assertNull(deleted);
    }
}