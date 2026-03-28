package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import model.dao.AssignmentDao;
import model.dao.CourseDao;
import model.dao.StudentDao;
import model.dao.TeacherDao;
import model.dao.implementation.StudentDaoImpl;
import model.dao.implementation.TeacherDaoImpl;
import model.entity.*;
import model.dao.implementation.CourseDaoImpl;
import model.dao.implementation.AssignmentDaoImpl;

public class MainController {

    @FXML
    private ListView<Course> courseListView;

    @FXML
    private ListView<Assignment> assignmentListView;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label coursesLabel;

    @FXML
    private Label assignmentsLabel;

    @FXML
    public void initialize() {
        loadLanguage();
        displayCourses();
        displayAssignments();
    }

    public void setUser(User user) {
        TeacherDao teacherDao = new TeacherDaoImpl();
        Teacher teacher = teacherDao.findByUserId(user.getId());

        if (teacher != null) {
            nameLabel.setText(teacher.getFirstName() + " " + teacher.getLastName());
            return;
        }

        StudentDao studentDao = new StudentDaoImpl();
        Student student = studentDao.findByUserId(user.getId());

        String name = student != null
                ? student.getFirstName() + " " + student.getLastName()
                : user.getUsername();

        nameLabel.setText(name);
    }

    // Show all courses for testing (todo: get courses by teacher ID)
    public void displayCourses() {
        // Get courses
        CourseDao courseDao = new CourseDaoImpl();
        List<Course> courseList = courseDao.findAll();

        // Set observablelist (items) to listview
        ObservableList<Course> courses = FXCollections.observableArrayList(courseList);
        courseListView.setItems(courses);

        // Custom cell containers
        courseListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);

                if (empty || course == null) {
                    setGraphic(null);
                } else {
                    // Item container
                    VBox container = listCellContainer();

                    // Name label
                    Label nameLabel = new Label(course.getName() + " (" + course.getCode() + ")");
                    nameLabel.setStyle("-fx-font-size: 14;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
                    container.getChildren().add(nameLabel);

                    setGraphic(container);

                    // Click handler for course view
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CourseView.fxml"));

                                Parent root = loader.load();
                                CourseController controller = loader.getController();
                                controller.setCourse(course);

                                Stage stage = (Stage) getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    // Show all assignments for testing (todo: show only ungraded assignments by teacher ID)
    public void displayAssignments() {
        // Get assignments
        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        List<Assignment> assignmentList = assignmentDao.findAll();
        ObservableList<Assignment> assignments = FXCollections.observableArrayList(assignmentList);

        // Add to listview
        assignmentListView.setItems(assignments);

        // Custom cells
        assignmentListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Assignment assignment, boolean empty) {
                super.updateItem(assignment, empty);
                if (empty || assignment == null) {
                    setGraphic(null);
                } else {
                    VBox container = listCellContainer();

                    // Name label
                    Label nameLabel = new Label(assignment.getTitle());
                    nameLabel.setStyle("-fx-font-size: 14;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
                    container.getChildren().add(nameLabel);

                    // Course label
                    String courseName = assignment.getCourse().getName();
                    String courseCode = assignment.getCourse().getCode();
                    Label courseLabel = new Label(courseName + " (" + courseCode + ")");
                    courseLabel.setStyle("-fx-text-fill: black;");
                    container.getChildren().add(courseLabel);

                    setGraphic(container);
                }
            }
        });
    }

    // Helper for a custom listview item container
    private VBox listCellContainer() {
        VBox container = new VBox();
        container.setSpacing(16);
        container.setStyle("-fx-background-color: #b6c8d4;" + "-fx-padding: 16;" + "-fx-background-radius: 8;");
        return container;
    }

    private void loadLanguage() {
        // Hardcoded placeholder locale & resource bundle (todo: language selection)
        Locale locale = new Locale("fi", "FI");
        ResourceBundle r = ResourceBundle.getBundle("MainBundle", locale);

        // Set label texts
        welcomeLabel.setText(r.getString("WELCOME_LABEL"));
        coursesLabel.setText(r.getString("COURSES_LABEL"));
        assignmentsLabel.setText(r.getString("ASSIGNMENTS_LABEL"));
    }
}
