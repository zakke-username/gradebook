package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.AssignmentDao;
import model.dao.GradeDao;
import model.dao.implementation.AssignmentDaoImpl;
import model.dao.implementation.GradeDaoImpl;
import model.entity.Assignment;
import model.entity.Course;
import model.entity.Grade;
import model.entity.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentController {
    private Student student;
    private Course course;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label courseNameLabel;

    @FXML
    private TableView<Map.Entry<String, Integer>> gradeTable;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> assignmentColumn;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> gradeColumn;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void displayInfo() {
        studentNameLabel.setText("Opiskelija: " + this.student.getFirstName() + " " + this.student.getLastName());
        courseNameLabel.setText("Kurssi: " + this.course.getName());
        displayGrades();
    }

    public void displayGrades() {
        Map<String, Integer> grades = getAssignmentGrades();
        ObservableList<Map.Entry<String, Integer>> rows = FXCollections.observableArrayList(grades.entrySet());
        gradeTable.setItems(rows);

        assignmentColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getKey()));
        gradeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getValue().toString()));
    }

    public Map<String, Integer> getAssignmentGrades() {
        // Get all course assignments
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        List<Assignment> assignments = assignmentDao.findByCourseId(this.course.getId());

        // Loop over assignments, add grade to hashmap if exists
        Map<String, Integer> grades = new HashMap<>();
        GradeDao gradeDao = new GradeDaoImpl();
        int studentId = this.student.getId();
        for (Assignment assignment : assignments) {
            Grade grade = gradeDao.findByStudentAndAssignment(studentId, assignment.getId());
            if (grade != null) {
                grades.put(assignment.getTitle(), grade.getScore());
            }
        }
        return grades;
    }
}
