package com.example.fitnesstrackerapp.admin.formcontroller;

import com.example.fitnesstrackerapp.admin.controller.ActivityLogController;
import com.example.fitnesstrackerapp.admin.controller.ProgressVisualizationController;
import com.example.fitnesstrackerapp.admin.dto.UserActivityDto;
import com.example.fitnesstrackerapp.admin.service.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ActivityLogFormController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lb_calendar;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btn_skipped;
    @FXML
    private Button btn_did;
    @FXML
    private Button btn_back;
    @FXML
    private ComboBox<String> btn_comboboxselectworkout;
    @FXML
    private ComboBox<String> btn_comboboxselectnutrition;
    @FXML
    private Button btn_submit;
    @FXML
    private Label lbl_submissionStatus;
    @FXML
    private Label lbl_email;

    private String email;

    private final Map<LocalDate, String> dateStatusMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                String title = ((Stage) anchorPane.getScene().getWindow()).getTitle();

                if (title.contains(",")) {
                    email = title.split(",")[1].trim();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        lbl_submissionStatus.setVisible(false);

        btn_comboboxselectworkout.setItems(FXCollections.observableArrayList(
                "BodyWeight", "Cardio", "Upper Body", "Lower Body", "HIIT", "Strength Training", "Yoga", "Core/Abs"));
        btn_comboboxselectnutrition.setItems(FXCollections.observableArrayList(
                "Carbohydrates", "Proteins", "Healthy Fats", "Vitamins", "Minerals", "Supplements"));

        datePicker.setDayCellFactory(createDayCellFactory());

        btn_did.setOnAction(event -> markDate("did"));
        btn_skipped.setOnAction(event -> markDate("skipped"));
        btn_submit.setOnAction(event -> submitSelectedData());

        btn_back.setOnAction(event -> {
            UserService userService = new UserService();
            userService.changeScence1(event, "select_view.fxml", "Select an Option," + email);
        });
    }

    private void markDate(String status) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            showAlert("No Date Selected", "Please select a date first.", Alert.AlertType.WARNING);
            return;
        }

        dateStatusMap.put(selectedDate, status);
        datePicker.setDayCellFactory(createDayCellFactory());

        if ("did".equals(status)) {
            ProgressVisualizationController.refreshDataBasedOnWorkout(datePicker.getValue(),
                    btn_comboboxselectworkout.getValue());
        }
    }

    private Callback<DatePicker, DateCell> createDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty && item != null) {
                    if (dateStatusMap.containsKey(item)) {
                        String status = dateStatusMap.get(item);
                        setStyle(status.equals("did") ? "-fx-background-color: #90ee90;" : "-fx-background-color: #ffcccb;");
                        setTooltip(new Tooltip(status.equals("did") ? "Completed" : "Skipped"));
                    }
                }
            }
        };
    }

    private void submitSelectedData() {
        String selectedWorkout = btn_comboboxselectworkout.getValue();
        String selectedNutrition = btn_comboboxselectnutrition.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate == null || selectedWorkout == null || selectedNutrition == null) {
            lbl_submissionStatus.setVisible(true);
            lbl_submissionStatus.setText("Please select a date, workout, and nutrition option.");
            lbl_submissionStatus.setStyle("-fx-text-fill: red;");
            return;
        }

        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setDate(selectedDate.toString());
        userActivityDto.setWorkoutType(selectedWorkout);
        userActivityDto.setNutrition(selectedNutrition);


        userActivityDto.setEmail(email);

        ActivityLogController activityLogController = new ActivityLogController();
        activityLogController.userActivityLog(userActivityDto, lbl_submissionStatus);


        lbl_submissionStatus.setVisible(true);
        lbl_submissionStatus.setText("Your selections have been submitted.");
        lbl_submissionStatus.setStyle("-fx-text-fill: green;");

        ProgressVisualizationController.refreshDataBasedOnNutrition(selectedNutrition);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setUserInformation(String email) {
        lbl_email.setText(email);

    }
}
