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

public class PrimaryControllerSuppliers {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField supplierIDField;
    @FXML
    private TextField supplierNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TableView<Suppliers> suppliersTableView;
    @FXML
    private TableColumn<Suppliers, Integer> supplierID;
    @FXML
    private TableColumn<Suppliers, String> supplierName;
    @FXML
    private TableColumn<Suppliers, String> email;
    @FXML
    private TableColumn<Suppliers, Integer> phoneNumber;

    private ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        suppliersTableView.setItems(suppliersList);

        supplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        refreshSuppliersTableView();

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
    private final DBSuppliersOperations DBSuppliersOperations = new DBSuppliersOperations(databaseConnection);

    @FXML
    private void InsertSuppliers() {

        System.out.println("Inserting Suppliers");
        int supplierID = Integer.parseInt(supplierIDField.getText());
        String supplierName = supplierNameField.getText();
        String email = emailField.getText();
        int phoneNumber = Integer.parseInt(phoneNumberField.getText());

        Suppliers newSuppliers = new Suppliers(supplierID, supplierName, email, phoneNumber);
        try {
            DBSuppliersOperations.insertSuppliers(newSuppliers.getSupplierID(), newSuppliers.getSupplierName(), newSuppliers.getEmail(),
                    newSuppliers.getPhoneNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new customer to the ObservableList
        suppliersList.add(newSuppliers);

        clearTextFields();
    }

    @FXML
    private void UpdateSuppliers() {
        System.out.println("Updating Suppliers");
        try {
            int supplierID = Integer.parseInt(supplierIDField.getText());
            String supplierName = supplierNameField.getText();
            String email = emailField.getText();
            int phoneNumber = Integer.parseInt(phoneNumberField.getText());

            DBSuppliersOperations.updateSuppliers(supplierID, supplierName, email,phoneNumber);

            refreshSuppliersTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void DeleteSuppliers() {
        System.out.println("Deleting Suppliers");
        try {
            int supplierID = Integer.parseInt(supplierIDField.getText());

            DBSuppliersOperations.deleteSuppliers(supplierID);
            refreshSuppliersTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void refreshSuppliersTableView() {
        clearTextFields();
        try {
            suppliersTableView.getItems().clear();
            List<Suppliers> supplier = DBSuppliersOperations.getSuppliers();
            for (Suppliers suppliers : supplier) {
                suppliersList.add(suppliers);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    

    private void clearTextFields() {
        supplierIDField.clear();
        supplierNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
    }
}
