package model.dao.implementation;

import model.entity.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest extends AbstractDaoTest {

    @BeforeEach
    void wipeTable() {
        // Wipe the student table.
        List<Student> students = studentDao.findAll();
        for (Student s : students) {
            studentDao.delete(s.getId());
        }
    }

    @AfterAll
    static void cleanDatabase() {
        // Clean the database after all the tests have run.
        wipeDatabase();
    }

    @Test
    void createAndFindStudent() {
        Student student = new Student();

        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEnrollmentYear(2025);

        studentDao.create(student);

        assertNotNull(student.getId());

        Student found = studentDao.findById(student.getId());

        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
        assertEquals(2025, found.getEnrollmentYear());
    }

    @Test
    void findAllStudents() {
        Student s1 = new Student();
        s1.setFirstName("Alice");
        s1.setLastName("Smith");
        s1.setEnrollmentYear(2024);
        studentDao.create(s1);

        Student s2 = new Student();
        s2.setFirstName("Bob");
        s2.setLastName("Jones");
        s2.setEnrollmentYear(2023);
        studentDao.create(s2);

        List<Student> allStudents = studentDao.findAll();
        assertEquals(2, allStudents.size());
    }

    @Test
    void findByEnrollmentYear() {
        Student s1 = new Student();
        s1.setFirstName("Eve");
        s1.setLastName("White");
        s1.setEnrollmentYear(2024);
        studentDao.create(s1);

        Student s2 = new Student();
        s2.setFirstName("Frank");
        s2.setLastName("Blue");
        s2.setEnrollmentYear(2023);
        studentDao.create(s2);

        List<Student> year2024 = studentDao.findByEnrollmentYear(2024);
        assertEquals(1, year2024.size());
        assertEquals("Eve", year2024.get(0).getFirstName());
    }

    @Test
    void updateStudent() {
        Student s = new Student();
        s.setFirstName("Charlie");
        s.setLastName("Brown");
        s.setEnrollmentYear(2022);
        studentDao.create(s);

        s.setLastName("Green");
        studentDao.update(s);

        Student updated = studentDao.findById(s.getId());
        assertEquals("Green", updated.getLastName());
    }

    @Test
    void deleteStudent() {
        Student s = new Student();
        s.setFirstName("David");
        s.setLastName("Black");
        s.setEnrollmentYear(2021);
        studentDao.create(s);

        studentDao.delete(s.getId());

        Student deleted = studentDao.findById(s.getId());
        assertNull(deleted, "Student should be deleted");
    }
}