package model.dao.implementation;

import model.entity.Teacher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDaoImplTest extends AbstractDaoTest {

    @BeforeAll
    static void setup() {
        // Wipe old data from database before tests
        wipeDatabase();
    }

    @BeforeEach
    void wipeTable() {
        // Wipe the teacher table.
        List<Teacher> teachers = teacherDao.findAll();
        for (Teacher t : teachers) {
            teacherDao.delete(t.getId());
        }
    }

    @AfterAll
    static void cleanDatabase() {
        // Clean the database after all the tests have run.
        wipeDatabase();
    }

    @Test
    void createAndFindTeacher() {
        Teacher t = new Teacher();
        t.setFirstName("Ada");
        t.setLastName("Lovelace");

        teacherDao.create(t);

        assertNotNull(t.getId());

        Teacher found = teacherDao.findById(t.getId());
        assertEquals("Ada", found.getFirstName());
        assertEquals("Lovelace", found.getLastName());
    }

    @Test
    void findAllTeachers() {
        Teacher t1 = new Teacher();
        t1.setFirstName("Alan");
        t1.setLastName("Turing");
        teacherDao.create(t1);

        Teacher t2 = new Teacher();
        t2.setFirstName("Edsger");
        t2.setLastName("Dijkstra");
        teacherDao.create(t2);

        List<Teacher> teachers = teacherDao.findAll();
        assertEquals(2, teachers.size());
    }

    @Test
    void findByLastName() {
        Teacher t = new Teacher();
        t.setFirstName("Grace");
        t.setLastName("Hopper");
        teacherDao.create(t);

        List<Teacher> result = teacherDao.findByLastName("Hopper");

        assertEquals(1, result.size());
        assertEquals("Grace", result.get(0).getFirstName());
    }

    @Test
    void updateTeacher() {
        Teacher t = new Teacher();
        t.setFirstName("Donald");
        t.setLastName("Knuth");
        teacherDao.create(t);

        t.setLastName("Knuth-Updated");
        teacherDao.update(t);

        Teacher updated = teacherDao.findById(t.getId());
        assertEquals("Knuth-Updated", updated.getLastName());
    }

    @Test
    void deleteTeacher() {
        Teacher t = new Teacher();
        t.setFirstName("Barbara");
        t.setLastName("Liskov");
        teacherDao.create(t);

        teacherDao.delete(t.getId());

        Teacher deleted = teacherDao.findById(t.getId());
        assertNull(deleted);
    }
}