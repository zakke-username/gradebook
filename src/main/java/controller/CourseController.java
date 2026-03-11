package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.AssignmentDao;
import model.dao.EnrollmentDao;
import model.dao.implementation.AssignmentDaoImpl;
import model.dao.implementation.EnrollmentDaoImpl;
import model.entity.Assignment;
import model.entity.Course;
import model.entity.Enrollment;
import model.entity.Student;

import java.util.ArrayList;
import java.util.List;


public class CourseController {
    private Course course;

    @FXML
    private Label courseNameLabel;

    @FXML
    private ListView<Student> studentListView;

    @FXML
    private ListView<Assignment> assignmentListView;

    @FXML
    private Button newAssignmentButton;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
    }

    public void setCourse(Course course) {
        this.course = course;
        courseNameLabel.setText("Kurssi: " + course.getName());
        displayStudents();
        displayAssignments();
    }

    public void displayStudents() {
        // Get students and set to listview
        List<Student> students = getStudents();
        ObservableList<Student> studentObservableList = FXCollections.observableList(students);
        studentListView.setItems(studentObservableList);

        // Custom cell factory
        studentListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);

                if (empty || student == null) {
                    setGraphic(null);
                } else {
//                    setText(student.getFirstName() + " " + student.getLastName());

                    // Container
                    VBox container = new VBox();
                    container.setSpacing(16);
                    container.setStyle("-fx-background-color: #b6c8d4;" + "-fx-padding: 16;" + "-fx-background-radius: 8;");

                    // Name label
                    Label nameLabel = new Label(student.getFirstName() + " " + student.getLastName());
                    nameLabel.setStyle("-fx-font-size: 14;" + "-fx-text-fill: black;");
                    container.getChildren().add(nameLabel);

                    setGraphic(container);

                    // Show student/grade view
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            displayStudentView(student);
                        }
                    });
                }
            }
        });
    }

    public List<Student> getStudents() {
        // Get enrollments
        EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();
        List<Enrollment> enrollments = enrollmentDao.findByCourseId(course.getId());

        // Get list of students
        List<Student> students = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            students.add(enrollment.getStudent());
        }
        return students;
    }

    public void displayAssignments() {
        // Get assignments and set into listview
        List<Assignment> assignments = getAssignments();
        ObservableList<Assignment> assignmentObservableList = FXCollections.observableList(assignments);
        assignmentListView.setItems(assignmentObservableList);

        // Custom item cell factory
        assignmentListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Assignment assignment, boolean empty) {
                super.updateItem(assignment, empty);
                if (empty || assignment == null) {
                    setGraphic(null);
                } else {
                    // Custom item box
                    VBox container = new VBox();
                    container.setSpacing(8);
                    container.setStyle("-fx-background-color: #b6c8d4;" + "-fx-padding: 16;" + "-fx-background-radius: 8;");

                    // Name label
                    Label nameLabel = new Label(assignment.getTitle());
                    nameLabel.setStyle("-fx-font-size: 12;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
                    container.getChildren().add(nameLabel);

                    // Score
                    Label scoreLabel = new Label("Maksimipisteet: " + assignment.getMaxScore().toString());
                    scoreLabel.setStyle("-fx-font-size: 12;" + "-fx-text-fill: black;");
                    container.getChildren().add(scoreLabel);

                    setGraphic(container);

                    // Open assignment on double click
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            displayAssignmentWindow(assignment);
                        }
                    });
                }
            }
        });
    }

    public List<Assignment> getAssignments() {
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        List<Assignment> assignments = assignmentDao.findByCourseId(course.getId());
        return assignments;
    }

    private void displayAssignmentWindow(Assignment assignment) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssignmentView.fxml"));
            Parent root = loader.load();

            // Set assignment object to controller
            AssignmentController controller = loader.getController();
            controller.setAssignment(assignment);

            // New window
            Stage stage = new Stage();
            stage.setTitle(assignment.getTitle());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            displayAssignments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createNewAssignment() {
        // Create assignment obj
        Assignment a = new Assignment();
        a.setTitle("Uusi tehtävä");
        a.setCourse(this.course);
        a.setMaxScore(5);
        a.setWeight(1.0f);
        a.setType("Homework"); // hardcoded for testing, add options later

        // Show popup
        displayAssignmentWindow(a);
    }

    private void displayStudentView(Student student) {
        try {
            // Load FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
            Parent root = loader.load();

            // Set up controller & display info
            StudentController controller = loader.getController();
            controller.setStudent(student);
            controller.setCourse(this.course);
            controller.displayInfo();

            // New popup window
            Stage window = new Stage();
            window.setTitle(this.course.getName() + " | " + student.getFirstName() + " " + student.getLastName());
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(new Scene(root));
            window.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.setUser(LoginController.getLoggedInUser());
            Stage window = (Stage) backButton.getScene().getWindow();
            window.setScene(new Scene(root));
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
