package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import model.dao.EnrollmentDao;
import model.dao.StudentDao;
import model.dao.implementation.EnrollmentDaoImpl;
import model.dao.implementation.StudentDaoImpl;
import model.entity.Course;
import model.entity.Enrollment;
import model.entity.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentController {
    private Course course;

    @FXML
    private ChoiceBox<Student> studentsChoiceBox;

    public void setCourse(Course course) {
        this.course = course;
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        // Get all students
        StudentDao studentDao = new StudentDaoImpl();
        List<Student> students = studentDao.findAll();

        // Filter out students already enrolled in course
        EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
        List<Enrollment> enrollments = enrollmentDao.findByCourseId(this.course.getId());
        for (Enrollment e : enrollments) {
            students.remove(e.getStudent());
        }

        // Populate choice box
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(students);
        studentsChoiceBox.setItems(studentObservableList);
    }

    @FXML
    private void enrollStudent() {
        // Get selected student from dropdown
        Student selectedStudent = studentsChoiceBox.getValue();
        if (selectedStudent == null) {
            // Show error message
            return;
        }
        // Create new enrollment
        EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(this.course);
        enrollment.setStudent(selectedStudent);
        enrollment.setStartDate(LocalDate.now());
        enrollment.setEndDate(LocalDate.now().plusDays(30));
        enrollmentDao.create(enrollment);

        // Close window
        studentsChoiceBox.getScene().getWindow().hide();
    }
}
