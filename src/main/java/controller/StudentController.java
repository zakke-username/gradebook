package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.dao.AssignmentDao;
import model.dao.GradeDao;
import model.dao.implementation.AssignmentDaoImpl;
import model.dao.implementation.GradeDaoImpl;
import model.entity.Assignment;
import model.entity.Course;
import model.entity.Grade;
import model.entity.Student;
import util.GradeCalculate;
import util.LocaleManager;
import util.SaveFile;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;

public class StudentController {
    private Student student;
    private Course course;

    @FXML
    private Label titleLabel;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label courseNameLabel;

    @FXML
    private Label weightedAverageLabel;

    @FXML
    private TableView<Map.Entry<String, Integer>> gradeTable;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> assignmentColumn;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> gradeColumn;

    @FXML
    private Button saveReportCardButton;

    private LocaleManager lm;

    public void initialize() {
        loadLanguage();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void displayInfo() {
        studentNameLabel.setText(MessageFormat.format(lm.getString("STUDENT_NAME"), this.student.getFirstName() + " " + this.student.getLastName()));
        courseNameLabel.setText(MessageFormat.format(lm.getString("STUDENT_COURSE_NAME"), this.course.getName()));
        weightedAverageLabel.setText(MessageFormat.format(lm.getString("STUDENT_AVERAGE"), String.format("%.2f", calculateWeightedAverage())));
        displayGrades();
    }

    public void displayGrades() {
        Map<String, Integer> grades = getAssignmentGrades();
        ObservableList<Map.Entry<String, Integer>> rows = FXCollections.observableArrayList(grades.entrySet());
        gradeTable.setItems(rows);

        assignmentColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getKey()));
        gradeColumn.setCellValueFactory(data -> {
            Integer score = data.getValue().getValue();
            return new ReadOnlyStringWrapper(score != null ? score.toString() : lm.getString("UNGRADED_POINTS"));
        });

        // Make grades editable
        gradeColumn.setEditable(true);
        gradeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        gradeColumn.setOnEditCommit(event -> {
            Map.Entry<String, Integer> row = event.getRowValue();
            Integer newScore = Integer.valueOf(event.getNewValue());
            row.setValue(newScore);
            saveGrade(row.getKey(), newScore);
        });
    }

    public Map<String, Integer> getAssignmentGrades() {
        // Get all course assignments
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        List<Assignment> assignments = assignmentDao.findByCourseId(this.course.getId());

        GradeDao gradeDao = new GradeDaoImpl();
        int studentId = this.student.getId();

        // Loop over assignments, add grade to hashmap
        Map<String, Integer> grades = new HashMap<>();
        for (Assignment assignment : assignments) {
            Grade grade = gradeDao.findByStudentAndAssignment(studentId, assignment.getId());
            if (grade != null) {
                grades.put(assignment.getTitle(), grade.getScore());
            } else {
                grades.put(assignment.getTitle(), null);
            }
        }
        return grades;
    }

    public void saveGrade(String assignmentTitle, Integer score) {
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        GradeDao gradeDao = new GradeDaoImpl();

        // Find assignment
        List<Assignment> assignments = assignmentDao.findByCourseId(this.course.getId());
        Assignment assignment = null;
        for (Assignment a : assignments) {
            if (a.getTitle().equals(assignmentTitle)) {
                assignment = a;
                break;
            }
        }
        if (assignment == null) return;

        // Save to db
        Grade existing = gradeDao.findByStudentAndAssignment(student.getId(), assignment.getId());
        if (existing == null) {
            // Create new
            Grade newGrade = new Grade();
            newGrade.setScore(Math.min(assignment.getMaxScore(), Math.max(0, score)));
            newGrade.setStudent(student);
            newGrade.setAssignment(assignment);
            newGrade.setDate(LocalDate.now());
            gradeDao.create(newGrade);
        } else {
            // Update grade
            existing.setScore(Math.min(assignment.getMaxScore(), Math.max(0, score)));
            gradeDao.update(existing);
        }
        displayInfo();
    }

    public double calculateWeightedAverage() {
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        GradeDao gradeDao = new GradeDaoImpl();

        // Get all assignments in course
        List<Assignment> assignments = assignmentDao.findByCourseId(this.course.getId());

        List<Double> scores = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        int studentId = this.student.getId();

        // Loop over assignments, if graded, add to scores & weights
        for (Assignment a : assignments) {
            Grade grade = gradeDao.findByStudentAndAssignment(studentId, a.getId());
            if (grade != null && grade.getScore() != null) {
                double normalized = grade.getScore().doubleValue() / a.getMaxScore().doubleValue();
                scores.add(normalized);
                weights.add(a.getWeight().doubleValue());
            }
        }
        if (scores.isEmpty()) return 0.0;
        return GradeCalculate.weightedAverage(scores, weights) * 5;
    }

    @FXML
    public void saveReportCard() {
        String courseName = this.course.getName();
        Map<String, Integer> grades = getAssignmentGrades();
        double weightedAverage = calculateWeightedAverage();
        String studentName = student.getFirstName() + " " + student.getLastName();
        SaveFile.saveReportAsPdf(courseName, grades, weightedAverage, studentName);
    }

    public void setStudentNameLabel(Label label) {
        this.studentNameLabel = label;
    }

    public void setCourseNameLabel(Label label) {
        this.courseNameLabel = label;
    }

    private void loadLanguage() {
        lm = LocaleManager.getInstance();

        titleLabel.setText(lm.getString("GRADES_TITLE"));
        assignmentColumn.setText(lm.getString("ASSIGNMENT_COLUMN"));
        gradeColumn.setText(lm.getString("POINTS_COLUMN"));
        saveReportCardButton.setText(lm.getString("SAVE_REPORT"));
    }
}
