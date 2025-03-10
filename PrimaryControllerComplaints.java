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

public class PrimaryControllerComplaints {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField complaintIDField;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TableView<Complaints> complaintsTableView;
    @FXML
    private TableColumn<Complaints, Integer> complaintID;
    @FXML
    private TableColumn<Complaints, Integer> customerID;
    @FXML
    private TableColumn<Complaints, String> date;
    @FXML
    private TableColumn<Complaints, String> description;

    private ObservableList<Complaints> complaintsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        complaintsTableView.setItems(complaintsList);

        complaintID.setCellValueFactory(new PropertyValueFactory<>("complaintID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    
        refreshComplaintsTableView();

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
    private final DBComplaintsOperations DBComplaintsOperations = new DBComplaintsOperations(databaseConnection);

    @FXML
    private void InsertComplaints() {

        System.out.println("Inserting Complaints");
        int complaintID = Integer.parseInt(complaintIDField.getText());
        int customerID = Integer.parseInt(customerIDField.getText());
        String date = dateField.getText();
        String description = descriptionField.getText();
    
        

        Complaints newComplaints = new Complaints(complaintID, customerID, date, description);
        try {
            DBComplaintsOperations.insertComplaints(newComplaints.getComplaintID(), newComplaints.getCustomerID(), newComplaints.getDate(),
                    newComplaints.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new customer to the ObservableList
        complaintsList.add(newComplaints);

        clearTextFields();
    }

    @FXML
    private void UpdateComplaints() {
        System.out.println("Updating Complaints");
        try {
            int complaintID = Integer.parseInt(complaintIDField.getText());
            int customerID = Integer.parseInt(customerIDField.getText());
            String date = dateField.getText();
            String description = descriptionField.getText();

            DBComplaintsOperations.updateComplaints(complaintID, customerID, date, description);

            refreshComplaintsTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
private void DeleteComplaints() {
    System.out.println("Deleting Complaints");
    try {
        int complaintID = Integer.parseInt(complaintIDField.getText());

        DBComplaintsOperations.deleteComplaints(complaintID); 
        refreshComplaintsTableView();

        clearTextFields();
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception as needed
    }
}


    @FXML
    private void refreshComplaintsTableView() {
        clearTextFields();
        try {
            complaintsTableView.getItems().clear();
            List<Complaints> complaint = DBComplaintsOperations.getComplaints();
            for (Complaints complaints : complaint) {
                complaintsList.add(complaints);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    

    private void clearTextFields() {
        complaintIDField.clear();
        customerIDField.clear();
        dateField.clear();
        descriptionField.clear();
    
    }
}
