package model.dao.implementation;

import model.dao.*;
import model.entity.*;

import java.util.List;

public abstract class AbstractDaoTest {

    protected static AssignmentDao assignmentDao = new AssignmentDaoImpl();
    protected static CourseDao courseDao = new CourseDaoImpl();
    protected static EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
    protected static GradeDao gradeDao = new GradeDaoImpl();
    protected static StudentDao studentDao = new StudentDaoImpl();
    protected static TeacherDao teacherDao = new TeacherDaoImpl();

    static protected void wipeDatabase() {
        List<Assignment> assignments = assignmentDao.findAll();
        for (Assignment a : assignments) {
            assignmentDao.delete(a.getId());
        }

        List<Course> courses = courseDao.findAll();
        for (Course a : courses) {
            courseDao.delete(a.getId());
        }

        List<Enrollment> enrollments = enrollmentDao.findAll();
        for (Enrollment e : enrollments) {
            enrollmentDao.delete(e.getId());
        }

        List<Grade> grades = gradeDao.findAll();
        for (Grade g : grades) {
            gradeDao.delete(g.getId());
        }

        List<Student> students = studentDao.findAll();
        for (Student s : students) {
            studentDao.delete(s.getId());
        }

        List<Teacher> teachers = teacherDao.findAll();
        for (Teacher t : teachers) {
            teacherDao.delete(t.getId());
        }
    }
}
