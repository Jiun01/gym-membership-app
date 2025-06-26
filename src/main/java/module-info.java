module com.example.fitnesstrackerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires spring.web;
    requires com.fasterxml.jackson.databind;


    //opens com.example.fitnesstrackerapp to javafx.fxml;
    opens com.example.fitnesstrackerapp to javafx.fxml;
    opens com.example.fitnesstrackerapp.admin.controller to javafx.fxml;
    exports com.example.fitnesstrackerapp;
    exports com.example.fitnesstrackerapp.admin.controller;
    exports com.example.fitnesstrackerapp.admin.formcontroller;
    opens com.example.fitnesstrackerapp.admin.formcontroller to javafx.fxml;
    exports com.example.fitnesstrackerapp.admin.model to com.fasterxml.jackson.databind;
}