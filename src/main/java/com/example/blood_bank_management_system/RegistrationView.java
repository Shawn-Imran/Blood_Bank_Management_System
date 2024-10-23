package com.example.blood_bank_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationView {
    @FXML
    public TextField emailField;
    public TextField phoneField;
    @FXML
    public ComboBox bloodGroupComboBox;
    @FXML
    public ComboBox userTypeComboBox;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    protected void onRegisterButtonClick() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String bloodGroup = bloodGroupComboBox.getValue().toString();
        String userType = userTypeComboBox.getValue().toString().toLowerCase();
        String password = passwordField.getText();

        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("All fields are required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, email, phone, blood_group, user_type, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, bloodGroup);
            preparedStatement.setString(5, userType);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registration successful for user: " + username);
            } else {
                System.out.println("Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
