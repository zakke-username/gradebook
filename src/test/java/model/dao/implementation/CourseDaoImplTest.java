package model.dao.implementation;

import model.entity.Course;
import model.entity.Teacher;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoImplTest extends AbstractDaoTest {

    private static Teacher testTeacher;

    @BeforeAll
    static void setup() {
        // Wipe old data from database before tests are run.
        wipeDatabase();

        // Create teacher dependency
        testTeacher = new Teacher();
        testTeacher.setFirstName("Jane");
        testTeacher.setLastName("Doe");
        teacherDao.create(testTeacher);
    }

    @BeforeEach
    void wipeTables() {
        for (Course c : courseDao.findAll()) {
            courseDao.delete(c.getId());
        }
    }

    @AfterAll
    static void cleanDatabase() {
        // Clean the database after all the tests have run.
        wipeDatabase();
    }

    @Test
    void createAndFindCourse() {
        Course c = new Course();
        c.setName("Physics");
        c.setCode("PHY101");
        c.setTeacher(testTeacher);

        courseDao.create(c);

        assertNotNull(c.getId());

        Course found = courseDao.findById(c.getId());
        assertEquals("Physics", found.getName());
        assertEquals("PHY101", found.getCode());
        assertEquals(testTeacher.getId(), found.getTeacher().getId());
    }

    @Test
    void findAllCourses() {
        Course c1 = new Course();
        c1.setName("Math");
        c1.setCode("MATH101");
        c1.setTeacher(testTeacher);
        courseDao.create(c1);

        Course c2 = new Course();
        c2.setName("Chemistry");
        c2.setCode("CHEM101");
        c2.setTeacher(testTeacher);
        courseDao.create(c2);

        List<Course> courses = courseDao.findAll();
        assertEquals(2, courses.size());
    }

    @Test
    void findByTeacherId() {
        Course c = new Course();
        c.setName("Biology");
        c.setCode("BIO101");
        c.setTeacher(testTeacher);
        courseDao.create(c);

        List<Course> courses = courseDao.findByTeacherId(testTeacher.getId());
        assertEquals(1, courses.size());
        assertEquals(testTeacher.getId(), courses.get(0).getTeacher().getId());
    }

    @Test
    void updateCourse() {
        Course c = new Course();
        c.setName("History");
        c.setCode("HIST101");
        c.setTeacher(testTeacher);
        courseDao.create(c);

        c.setName("Modern History");
        courseDao.update(c);

        Course updated = courseDao.findById(c.getId());
        assertEquals("Modern History", updated.getName());
    }

    @Test
    void deleteCourse() {
        Course c = new Course();
        c.setName("Geography");
        c.setCode("GEO101");
        c.setTeacher(testTeacher);
        courseDao.create(c);

        courseDao.delete(c.getId());

        Course deleted = courseDao.findById(c.getId());
        assertNull(deleted);
    }
}