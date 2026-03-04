package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.AssignmentDao;
import model.dao.implementation.AssignmentDaoImpl;
import model.entity.Assignment;


public class AssignmentController {
    private Assignment assignment;

    @FXML
    private Label assignmentTitleLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField pointsTextField;

    @FXML
    private Button saveButton;

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
        displayInfo();
    }

    private void displayInfo() {
        assignmentTitleLabel.setText("Muokkaa kurssia " + this.assignment.getTitle());
        titleTextField.setText(assignment.getTitle());
        descriptionTextArea.setText("Placeholder description...");
        pointsTextField.setText(this.assignment.getMaxScore().toString());
    }

    @FXML
    private void handleSaveAssignment() {
        String title = titleTextField.getText();
        int points = Integer.parseInt(pointsTextField.getText());
        this.assignment.setTitle(title);
        this.assignment.setMaxScore(points);

        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        assignmentDao.update(this.assignment);

        Stage window = (Stage) saveButton.getScene().getWindow();
        window.close();
    }
}
