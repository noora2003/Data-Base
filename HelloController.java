package application.inventroy2;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private Button add_inv;
    @FXML
    private Button clear_invt;

    @FXML
    private TextField coast_textfield;

    @FXML
    private TableColumn<InventoryData,Double> cost_inv;

    @FXML
    private DatePicker date_textfield;
    @FXML
    private Button delete_inv;

    @FXML
    private TableColumn<InventoryData, Date> expiredate_inv;

    @FXML
    private TableColumn<InventoryData,Integer> id_inv;

    @FXML
    private TextField id_textfeild;
    @FXML
    private TextField search_inv;

    @FXML
    private TableColumn<InventoryData,Integer> quantity_inv;

    @FXML
    private TextField quantity_textfield;

    @FXML
    private Button update_inv;
    @FXML
    private TableView<InventoryData> inv_table;


    private java.sql.Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    public ObservableList<InventoryData> InventoryListData() {

        ObservableList<InventoryData> LiatData = FXCollections.observableArrayList();
        String sql = "Select * from Inventory";
        connect = database_inventory.connectDb();
        try {
            prepare = connect.prepareStatement(sql);

            result = prepare.executeQuery();
            InventoryData inventoryI;
            while (result.next()) {
                inventoryI = new InventoryData(result.getInt("InventoryItemID"),
                        result.getInt("Quantity"),
                        result.getDouble("Cost"),
                        result.getDate("ExpiryDate"));

                LiatData.add(inventoryI);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return LiatData;
    }

    private ObservableList<InventoryData> InventoryList;

    public void inventoryShowListData() {
        InventoryList = InventoryListData();
        id_inv.setCellValueFactory(new PropertyValueFactory<>("InventoryItemID"));
        quantity_inv.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        expiredate_inv.setCellValueFactory(new PropertyValueFactory<>("ExpiryDate"));
        cost_inv.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        inv_table.setItems(InventoryList);

    }

    public void InventorySelect() {
        InventoryData inventoryI = inv_table.getSelectionModel().getSelectedItem();
        int num = inv_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        id_textfeild.setText(String.valueOf(inventoryI.getInventoryItemID()));
        quantity_textfield.setText(String.valueOf(inventoryI.getQuantity()));
        coast_textfield.setText(String.valueOf(inventoryI.getCost()));

        LocalDate mydate=date_textfield.getValue();
        //String formatte=mydate.format(DateTimeFormatter.ofPattern("dd,MM,yyy"));
       // date_textfield.setValue(formatte);

    }

    public void Inventoryadd(){
        System.out.println("bignnoin of added");
        String sql = "INSERT INTO Inventory "
                + "(InventoryItemID,Quantity,ExpiryDate, Cost) "
                + "VALUES(?,?,?,?)";

        connect = database_inventory.connectDb();

        try {

            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || quantity_textfield.getText().isEmpty()
                    || date_textfield.getValue() == null
                    || coast_textfield.getText().isEmpty())
                    {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT  InventoryItemID FROM Inventory WHERE InventoryItemID  = '"
                        + id_textfeild.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("InventoryItemID: " + id_textfeild.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, id_textfeild.getText());
                    prepare.setString(2,quantity_textfield .getText());

                    // Convert LocalDate to java.sql.Date
                    LocalDate localDate = date_textfield.getValue();
                    java.sql.Date sqlDate = null;
                    if (localDate != null) {
                        sqlDate = java.sql.Date.valueOf(localDate);
                    }
                    prepare.setDate(3, sqlDate);

                    prepare.setString(4, coast_textfield.getText());

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    inventoryShowListData();
                   InventoryReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end of added");

    }


    public void InventoryReset() {
        id_textfeild.setText("");
        quantity_textfield.setText("");
        date_textfield.setValue(null); // Reset DatePicker value to null
        coast_textfield.setText("");


    }


    public void InventoryUpdate() {
        String sql = "UPDATE Inventory SET InventoryItemID = '"
                + id_textfeild.getText() + "',  Quantity = '"
                + quantity_textfield.getText() + "', ExpiryDate = '"
                + date_textfield.getValue().toString() + "', Cost = '"
                + coast_textfield.getText() + "' WHERE InventoryItemID ='"
                + id_textfeild.getText() + "'";


        connect = database_inventory.connectDb();

        try {

            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || quantity_textfield.getText().isEmpty()
                    || date_textfield.getValue() == null
                    || coast_textfield.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE InventoryItemID: " + id_textfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("\"Successfully Updated!\"");
                    alert.showAndWait();
                    inventoryShowListData();
                    InventoryReset();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//public void updating() throws SQLException {
//    try {
//        System.out.println(" bigning Updating ");
//
//        // Retrieve input values from text fields
//        int id = Integer.parseInt(id_textfeild.getText());
//        System.out.println("ID: " + id);
//
//        int quantity = Integer.parseInt(quantity_textfield.getText());
//        System.out.println("Quantity: " + quantity);
//
//        double price = Double.parseDouble(coast_textfield.getText());
//        System.out.println("Price: " + price);
//
//        // Retrieve date from date picker
//        java.sql.Date date = java.sql.Date.valueOf(date_textfield.getValue());
//        System.out.println("Date: " + date);
//
//        // Clear existing data from the table
//        inv_table.getItems().clear();
//
//        // Define SQL update query
//        String query = "UPDATE Inventory SET Quantity = ?, ExpiryDate = ?, Cost = ? WHERE InventoryItemID = ?";
//
//        // Establish database connection
//        connect = database_inventory.connectDb();
//        System.out.println("Database connected.");
//
//        // Prepare statement to execute the query
//        PreparedStatement preparedStatement = connect.prepareStatement(query);
//        preparedStatement.setInt(1, quantity);
//        preparedStatement.setDate(2, date);
//        preparedStatement.setDouble(3, price);
//        preparedStatement.setInt(4, id);
//
//        // Execute update
//        preparedStatement.executeUpdate();
//        System.out.println("Update executed.");
//
//        // Refresh the inventory list and reset the form
//        inventoryShowListData();
//        InventoryReset();
//    } catch (SQLException e) {
//        System.err.println("SQL Error: " + e.getMessage());
//        e.printStackTrace();
//    } catch (NumberFormatException e) {
//        System.err.println("Number Format Error: " + e.getMessage());
//        e.printStackTrace();
//    } catch (Exception e) {
//        System.err.println("Error: " + e.getMessage());
//        e.printStackTrace();
//    }
//    System.out.println(" end Updating ");
//
//}

    public void InventoryDelete() {

        String sql = "DELETE FROM Inventory WHERE InventoryItemID = '"
                + id_textfeild.getText() + "'";

        connect = database_inventory.connectDb();

        try {
            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || quantity_textfield.getText().isEmpty()
                    || coast_textfield.getText().isEmpty()
                    || date_textfield.getValue() == null
                   ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE InventoryItemID: " + id_textfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("\"Successfully DELETE!\"");
                    alert.showAndWait();
                    inventoryShowListData();
                    InventoryReset();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void InventorySearch() {

        String searchid = search_inv.getText();

        try {
            String query = "SELECT * FROM Inventory WHERE InventoryItemID LIKE ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchid + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<InventoryData> searchResults = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("InventoryItemID");
                int quantity = resultSet.getInt("Quantity");
                double coast = resultSet.getDouble("Cost");
                java.sql.Date date=resultSet.getDate("ExpiryDate");

                  InventoryData inventory = new InventoryData(id,quantity,coast,date);
                searchResults.add(inventory);
            }

            ObservableList<InventoryData> observableList = FXCollections.observableArrayList(searchResults);
            inv_table.setItems(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }





}

