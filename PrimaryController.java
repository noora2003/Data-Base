package com.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PrimaryController {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField pointsField;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> customerEmail;
    @FXML
    private TableColumn<Customer, Integer> customerPoints;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        customerTableView.setItems(customerList);

        customerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        refreshCustomerTableView();

    }

    @FXML
    private static Connection databaseConnection;

    static {

        try {
            databaseConnection = new DBConn("127.0.0.1", "3306", "menuitems", "root", "1234").connectDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private final DBOperations dbOperations = new DBOperations(databaseConnection);

    @FXML
    private void InsertCustomer() {

        System.out.println("Inserting customer");
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        int points = Integer.parseInt(pointsField.getText());

        Customer newCustomer = new Customer(id, name, phone, email, points);
        try {
            dbOperations.insertCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getPhone(),
                    newCustomer.getEmail(), newCustomer.getPoints());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new customer to the ObservableList
        customerList.add(newCustomer);

        clearTextFields();
    }

    @FXML
    private void UpdateCustomer() {
        System.out.println("Updating customer");
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            int points = Integer.parseInt(pointsField.getText());

            dbOperations.updateCustomer(id, name, phone, email, points);

            refreshCustomerTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void DeleteCustomer() {
        System.out.println("Deleting customer");
        try {
            int id = Integer.parseInt(idField.getText());

            dbOperations.deleteCustomer(id);
            refreshCustomerTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void refreshCustomerTableView() {
        clearTextFields();
        try {
            customerTableView.getItems().clear();
            List<Customer> customers = dbOperations.getAllCustomers();
            for (Customer customer : customers) {
                customerList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    private void clearTextFields() {
        idField.clear();
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        pointsField.clear();
    }
}
