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

public class PrimaryControllerDrivers {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField driverIDField;
    @FXML
    private TextField driverNameField;
    @FXML
    private TextField licenseNumberField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TableView<Drivers> driversTableView;
    @FXML
    private TableColumn<Drivers, Integer> driverID;
    @FXML
    private TableColumn<Drivers, String> driverName;
    @FXML
    private TableColumn<Drivers, Integer> licenseNumber;
    @FXML
    private TableColumn<Drivers, Integer> phoneNumber;

    private ObservableList<Drivers> driversList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        driversTableView.setItems(driversList);

        driverID.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        driverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        licenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        

        refreshDriversTableView();

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
    private final DBDriversOperations DBDriversOperations = new DBDriversOperations(databaseConnection);

    @FXML
    private void InsertDrivers() {

        System.out.println("Inserting Drivers");
        int driverID = Integer.parseInt(driverIDField.getText());
        String driverName = driverNameField.getText();
        int licenseNumber = Integer.parseInt(licenseNumberField.getText());
        int phoneNumber = Integer.parseInt(phoneNumberField.getText());

        Drivers newDrivers = new Drivers(driverID, driverName, licenseNumber, phoneNumber);
        try {
            DBDriversOperations.insertDrivers(newDrivers.getDriverID(), newDrivers.getDriverName(), newDrivers.getLicenseNumber(),
                    newDrivers.getPhoneNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new customer to the ObservableList
        driversList.add(newDrivers);

        clearTextFields();
    }

    @FXML
    private void UpdateDrivers() {
        System.out.println("Updating Drivers");
        try {
            int driverID = Integer.parseInt(driverIDField.getText());
            String driverName = driverNameField.getText();
            int licenseNumber = Integer.parseInt(licenseNumberField.getText());
            int phoneNumber = Integer.parseInt(phoneNumberField.getText());

            DBDriversOperations.updateDrivers(driverID, driverName, licenseNumber, phoneNumber);

            refreshDriversTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void DeleteDrivers() {
        System.out.println("Deleting Drivers");
        try {
            int driverID = Integer.parseInt(driverIDField.getText());

            DBDriversOperations.deleteDrivers(driverID);
            refreshDriversTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void refreshDriversTableView() {
        clearTextFields();
        try {
            driversTableView.getItems().clear();
            List<Drivers> driver = DBDriversOperations.getDrivers();
            for (Drivers drivers : driver) {
                driversList.add(drivers);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    

    private void clearTextFields() {
        driverIDField.clear();
        driverNameField.clear();
        licenseNumberField.clear();
        phoneNumberField.clear();
    }
}
