package controller;

import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.AssignmentDao;
import model.dao.implementation.AssignmentDaoImpl;
import model.entity.Assignment;
import util.LocaleManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class AssignmentController {
    LocaleManager lm;
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
    private TextField weightTextField;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label maxPointsLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Parent root;

    public void initialize() {
        loadLanguage();
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
        displayInfo();
    }

    private void displayInfo() {
        assignmentTitleLabel.setText(lm.getString("MODIFY_ASSIGNMENT"));
        titleTextField.setText(assignment.getTitle());
        weightTextField.setText(String.valueOf(assignment.getWeight()));
        pointsTextField.setText(this.assignment.getMaxScore().toString());
    }

    @FXML
    private void handleSaveAssignment() {
        String title = titleTextField.getText();
        int points = Integer.parseInt(pointsTextField.getText());
        float weight = Float.parseFloat(weightTextField.getText());

        this.assignment.setTitle(title);
        this.assignment.setMaxScore(points);
        this.assignment.setWeight(weight);

        AssignmentDao assignmentDao = new AssignmentDaoImpl();
        assignmentDao.update(this.assignment);

        Stage window = (Stage) saveButton.getScene().getWindow();
        window.close();
    }

    private void loadLanguage() {
        lm = LocaleManager.getInstance();

        titleLabel.setText(lm.getString("ASSIGNMENT_TITLE"));
        descriptionLabel.setText(lm.getString("ASSIGNMENT_DESCRIPTION"));
        maxPointsLabel.setText(lm.getString("MAX_POINTS"));
        weightLabel.setText(lm.getString("WEIGHT"));
        saveButton.setText(lm.getString("SAVE_ASSIGNMENT"));

        if ("fa".equals(lm.getLanguage())) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }
}
