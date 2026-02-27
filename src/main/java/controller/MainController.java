package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.entity.Course;

import java.util.ArrayList;

public class MainController {

    @FXML
    private ListView<Course> courseListView;

    public MainController() {
    }

    @FXML
    public void initialize() {
        displayCourses();
    }

    // TODO: actual courses from database (this is for testing)
    public void displayCourses() {
        // Dummy courses
        ArrayList<Course> courseList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Course c = new Course();
            c.setName("Kurssi " + i);
            courseList.add(c);
        }

        // Set observablelist (items) to listview
        ObservableList<Course> courses = FXCollections.observableArrayList(courseList);
        courseListView.setItems(courses);

        // Custom cell containers for listview
        courseListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.setSpacing(16);
                    container.setStyle(
                            "-fx-background-color: #b6c8d4;" +
                            "-fx-padding: 16;" +
                            "-fx-background-radius: 8;" +
                            "-fx-end-margin: 16;"
                    );

                    Label nameLabel = new Label(course.getName());
                    nameLabel.setStyle("-fx-font-size: 16;" + "-fx-text-fill: black");
                    container.getChildren().add(nameLabel);

                    setGraphic(container);
                }
            }
        });
    }
}
