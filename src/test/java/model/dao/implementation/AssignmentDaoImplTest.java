package model.dao.implementation;

import model.entity.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentDaoImplTest extends AbstractDaoTest {

    private static Teacher testTeacher;
    private static Course testCourse;

    @BeforeAll
    static void setup() {
        // Clean database before tests run
        wipeDatabase();

        // Create a test teacher
        testTeacher = new Teacher();
        testTeacher.setFirstName("John");
        testTeacher.setLastName("Doe");
        teacherDao.create(testTeacher);

        // Create a test course
        testCourse = new Course();
        testCourse.setName("Math 101");
        testCourse.setCode("MATH101");
        testCourse.setTeacher(testTeacher);
        courseDao.create(testCourse);
    }

    @BeforeEach
    void wipeTable() {
        // Wipe the table before every test runs.
        List<Assignment> assignments = assignmentDao.findAll();
        for (Assignment a : assignments) {
            assignmentDao.delete(a.getId());
        }
    }

    @AfterAll
    static void tearDown() {
        // Clean database after tests have ran.
        wipeDatabase();
    }

    @Test
    void createAndFindAssignment() {
        Assignment a = new Assignment();
        a.setTitle("Homework 1");
        a.setWeight(0.2f);
        a.setMaxScore(100);
        a.setType("Homework");
        a.setCourse(testCourse);

        assignmentDao.create(a);

        assertNotNull(a.getId());

        Assignment found = assignmentDao.findById(a.getId());

        assertEquals("Homework 1", found.getTitle());
        assertEquals(0.2f, found.getWeight());
        assertEquals(100, found.getMaxScore());
        assertEquals("Homework", found.getType());
        assertEquals(testCourse.getId(), found.getCourse().getId());
    }

    @Test
    void findAllAssignments() {
        Assignment a1 = new Assignment();
        a1.setTitle("HW1");
        a1.setWeight(0.1f);
        a1.setMaxScore(50);
        a1.setType("Homework");
        a1.setCourse(testCourse);
        assignmentDao.create(a1);

        Assignment a2 = new Assignment();
        a2.setTitle("Quiz 1");
        a2.setWeight(0.05f);
        a2.setMaxScore(20);
        a2.setType("Quiz");
        a2.setCourse(testCourse);
        assignmentDao.create(a2);

        List<Assignment> all = assignmentDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findByCourseId() {
        Assignment a = new Assignment();
        a.setTitle("HW2");
        a.setWeight(0.2f);
        a.setMaxScore(100);
        a.setType("Homework");
        a.setCourse(testCourse);
        assignmentDao.create(a);

        List<Assignment> byCourse = assignmentDao.findByCourseId(testCourse.getId());
        assertEquals(1, byCourse.size());
        assertEquals("HW2", byCourse.get(0).getTitle());
    }

    @Test
    void updateAssignment() {
        Assignment a = new Assignment();
        a.setTitle("Quiz 1");
        a.setWeight(0.1f);
        a.setMaxScore(20);
        a.setType("Quiz");
        a.setCourse(testCourse);
        assignmentDao.create(a);

        a.setTitle("Quiz 1 Updated");
        assignmentDao.update(a);

        Assignment updated = assignmentDao.findById(a.getId());
        assertEquals("Quiz 1 Updated", updated.getTitle());
    }

    @Test
    void deleteAssignment() {
        Assignment a = new Assignment();
        a.setTitle("Final Exam");
        a.setWeight(0.5f);
        a.setMaxScore(200);
        a.setType("Exam");
        a.setCourse(testCourse);
        assignmentDao.create(a);

        assignmentDao.delete(a.getId());

        Assignment deleted = assignmentDao.findById(a.getId());
        assertNull(deleted);
    }
}