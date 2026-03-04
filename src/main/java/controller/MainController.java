package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.AssignmentDao;
import model.dao.CourseDao;
import model.entity.Assignment;
import model.entity.Course;
import model.dao.implementation.CourseDaoImpl;
import model.dao.implementation.AssignmentDaoImpl;

public class MainController {

    @FXML
    private ListView<Course> courseListView;

    @FXML
    private ListView<Assignment> assignmentListView;

    @FXML
    public void initialize() {
        displayCourses();
        displayAssignments();
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
                    nameLabel.setStyle("-fx-font-size: 16;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
                    container.getChildren().add(nameLabel);

                    setGraphic(container);

                    // Add click handler here (setOnMouseClicked -> change scene to course view)
                    setOnMouseClicked(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
                        } catch (Exception e) {
                            e.printStackTrace();
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
                    nameLabel.setStyle("-fx-font-size: 16;" + "-fx-text-fill: black;" + "-fx-font-weight: bold;");
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
        container.setStyle(
                "-fx-background-color: #b6c8d4;" +
                        "-fx-padding: 16;" +
                        "-fx-background-radius: 8;"
        );
        return container;
    }
}
