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

public class PrimaryControllerMenuitems {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField menuIDField;
    @FXML
    private TextField itemNameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField itemIDField;
    @FXML
    private TableView<Menuitems> menuitemsTableView;
    @FXML
    private TableColumn<Menuitems, Integer> menuID;
    @FXML
    private TableColumn<Menuitems, String> itemName;
    @FXML
    private TableColumn<Menuitems, String> description;
    @FXML
    private TableColumn<Menuitems, String> price;
    @FXML
    private TableColumn<Menuitems, Integer> itemID;

    private ObservableList<Menuitems> menuitemsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        menuitemsTableView.setItems(menuitemsList);

        menuID.setCellValueFactory(new PropertyValueFactory<>("menuID"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));

        refreshMenuitemsTableView();

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
    private final DBMenuitemsOperations DBMenuitemsOperations = new DBMenuitemsOperations(databaseConnection);

    @FXML
    private void InsertMenuitems() {

        System.out.println("Inserting Menuitems");
        int menuID = Integer.parseInt(menuIDField.getText());
        String itemName = itemNameField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        int itemID = Integer.parseInt(itemIDField.getText());

        Menuitems newMenuitems = new Menuitems(menuID, itemName, description, price, itemID);
        try {
            DBMenuitemsOperations.insertMenuitems(newMenuitems.getMenuID(), newMenuitems.getItemName(), newMenuitems.getDescription(),
                    newMenuitems.getPrice(), newMenuitems.getItemID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the new customer to the ObservableList
        menuitemsList.add(newMenuitems);

        clearTextFields();
    }

    @FXML
    private void UpdateMenuitems() {
        System.out.println("Updating Menuitems");
        try {
            int menuID = Integer.parseInt(menuIDField.getText());
            String itemName = itemNameField.getText();
            String description = descriptionField.getText();
            String price = priceField.getText();
            int itemID = Integer.parseInt(itemIDField.getText());

            DBMenuitemsOperations.updateMenuitems(menuID, itemName, description, price, itemID);

            refreshMenuitemsTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void DeleteMenuitems() {
        System.out.println("Deleting Menuitems");
        try {
            int itemID = Integer.parseInt(itemIDField.getText());

            DBMenuitemsOperations.deleteMenuitems(itemID);
            refreshMenuitemsTableView();

            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void refreshMenuitemsTableView() {
        clearTextFields();
        try {
            menuitemsTableView.getItems().clear();
            List<Menuitems> menuitem = DBMenuitemsOperations.getMenuitems();
            for (Menuitems menuitems : menuitem) {
                menuitemsList.add(menuitems);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    

    private void clearTextFields() {
        menuIDField.clear();
        itemNameField.clear();
        descriptionField.clear();
        priceField.clear();
        itemIDField.clear();
    }
}
