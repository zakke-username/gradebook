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

import java.util.Locale;
import java.util.ResourceBundle;

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

    public void initialize() {
        loadLanguage();
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
        displayInfo();
    }

    private void displayInfo() {
        assignmentTitleLabel.setText("Muokkaa tehtävää " + this.assignment.getTitle());
        titleTextField.setText(assignment.getTitle());
        weightTextField.setText(String.valueOf(assignment.getWeight()));
        descriptionTextArea.setText("Placeholder description...");
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
        // Hardcoded placeholder locale & resource bundle (todo: language selection)
        Locale locale = new Locale("fi", "FI");
        ResourceBundle r = ResourceBundle.getBundle("AssignmentBundle", locale);

        titleLabel.setText(r.getString("TITLE"));
        descriptionLabel.setText(r.getString("DESCRIPTION"));
        maxPointsLabel.setText(r.getString("MAX_POINTS"));
        weightLabel.setText(r.getString("WEIGHT"));
        saveButton.setText(r.getString("SAVE"));
    }
}
