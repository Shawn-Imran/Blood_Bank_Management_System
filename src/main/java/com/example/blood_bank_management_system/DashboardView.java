package com.example.blood_bank_management_system;

import com.example.blood_bank_management_system.dto.BloodDetail;
import com.example.blood_bank_management_system.dto.UserDetail;
import com.example.blood_bank_management_system.dto.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DashboardView {
    @FXML
    private TableView<BloodDetail> bloodDetailsTable;

    @FXML
    private TableColumn<BloodDetail, String> bloodGroupColumn;
    @FXML
    private TableColumn<BloodDetail, String> quantityColumn;


    @FXML
    private TableView<UserDetail> usersTable;
    @FXML
    private TableColumn<UserDetail, String> usernameColumn;
    @FXML
    private TableColumn<UserDetail, String> emailColumn;
    @FXML
    private TableColumn<UserDetail, String> phoneColumn;
    @FXML
    private TableColumn<UserDetail, String> userBloodGroupColumn;

    @FXML
    public void initialize() {
        bloodDetailsTable.setEditable(true); // Make the table editable

        bloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Center the text in the columns
        bloodGroupColumn.setCellFactory(column -> new TableCell<BloodDetail, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        quantityColumn.setCellFactory(column -> {
            TableCell<BloodDetail, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    cell.startEdit();
                }
            });

            return cell;
        });

        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityColumn.setOnEditCommit(event -> {
            BloodDetail bloodDetail = event.getRowValue();
            bloodDetail.setQuantity(event.getNewValue());
            updateQuantityInDatabase(bloodDetail);
        });

        loadBloodDetails();




        // Initialize usersTable
        usersTable.setEditable(true);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userBloodGroupColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));

        // Center the text in the columns
        usernameColumn.setCellFactory(column -> new TableCell<UserDetail, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> {
            UserDetail userDetail = event.getRowValue();
            userDetail.setEmail(event.getNewValue());
            updateUserDetailInDatabase(userDetail);
        });

        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(event -> {
            UserDetail userDetail = event.getRowValue();
            userDetail.setPhone(event.getNewValue());
            updateUserDetailInDatabase(userDetail);
        });

        userBloodGroupColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        )));
        userBloodGroupColumn.setOnEditCommit(event -> {
            UserDetail userDetail = event.getRowValue();
            userDetail.setBloodGroup(event.getNewValue());
            updateUserDetailInDatabase(userDetail);
        });

    }

    private void loadBloodDetails() {
        ObservableList<BloodDetail> bloodDetails = FXCollections.observableArrayList();
        String query = "SELECT blood_group, quantity FROM blood_store";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String bloodGroup = resultSet.getString("blood_group");
                String quantity = resultSet.getString("quantity");
                bloodDetails.add(new BloodDetail(bloodGroup, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bloodDetailsTable.setItems(bloodDetails);
    }

    private void updateQuantityInDatabase(BloodDetail bloodDetail) {
    String query = "UPDATE blood_store SET quantity = ? WHERE blood_group::text = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        int quantity = Integer.parseInt(bloodDetail.getQuantity());
        String bloodGroup = bloodDetail.getBloodGroup();

        preparedStatement.setInt(1, quantity);
        preparedStatement.setString(2, bloodGroup);

        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected); // Log the number of rows affected
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.err.println("Invalid quantity format: " + bloodDetail.getQuantity());
    }
}




    private void loadUserDetails(String userType) {
        ObservableList<UserDetail> userDetails = FXCollections.observableArrayList();
        String query = "SELECT id, username, email, phone, blood_group FROM users WHERE user_type::text = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userType); // Set the userType parameter

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    String bloodGroup = resultSet.getString("blood_group");
                    userDetails.add(new UserDetail(id, username, email, phone, bloodGroup));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        usersTable.setItems(userDetails);
    }


    private void updateUserDetailInDatabase(UserDetail userDetail) {
    String query = "UPDATE users SET email = ?, phone = ?, blood_group = ?::blood_group WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, userDetail.getEmail());
        preparedStatement.setString(2, userDetail.getPhone());
        preparedStatement.setString(3, userDetail.getBloodGroup());
        preparedStatement.setInt(4, userDetail.getId()); // Set the id as an integer

        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println("Rows affected: " + rowsAffected); // Log the number of rows affected
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    public void onBloodStorageClick(ActionEvent actionEvent) {
        bloodDetailsTable.setVisible(true);
        usersTable.setVisible(false);
        loadBloodDetails();
    }

    public void onUsersClick(ActionEvent actionEvent) {
        bloodDetailsTable.setVisible(false);

        usersTable.setVisible(true);
        loadUserDetails(UserType.ADMIN.toString().toLowerCase());
    }

    public void onDonorsClick(ActionEvent actionEvent) {
        bloodDetailsTable.setVisible(false);

        usersTable.setVisible(true);
        loadUserDetails(UserType.DONOR.toString().toLowerCase());
    }

    public void onReceiversClick(ActionEvent actionEvent) {
        bloodDetailsTable.setVisible(false);

        usersTable.setVisible(true);
        loadUserDetails(UserType.RECEIVER.toString().toLowerCase());
    }
}
