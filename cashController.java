package com.db;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static com.db.DB_Main.items;

public class cashController {

  @FXML
  private TextField name, phone, address;

  @FXML
  private Text UserName;
  @FXML
  private Text totalPrice;

  @FXML
  private Button itemBtn1, itemBtn2, itemBtn3, itemBtn4, itemBtn5, itemBtn6, itemBtn7, itemBtn8, clearBtn;

  private List<Button> itemButtons;

  @FXML
  private MenuButton orderType, paymentMethod;

  private String currentUser;

  @FXML
  private TableView<Items> tableItems;
  @FXML
  private TableColumn<Items, String> itemTitle;
  @FXML
  private TableColumn<Items, Integer> itemPrice;

  private ObservableList<Items> orderedItems;

  public void initialize() {
    // Fetch the current user name from the login controller
    currentUser = loginController.currentUsername;

    // Append the current user name to the User text field
    UserName.setText(currentUser);

    // Initialize the ArrayList and add the buttons to it
    itemButtons = new ArrayList<>();
    itemButtons.add(itemBtn1);
    itemButtons.add(itemBtn2);
    itemButtons.add(itemBtn3);
    itemButtons.add(itemBtn4);
    itemButtons.add(itemBtn5);
    itemButtons.add(itemBtn6);
    itemButtons.add(itemBtn7);
    itemButtons.add(itemBtn8);

    // Initialize the ordered items list
    orderedItems = FXCollections.observableArrayList();
    tableItems.setItems(orderedItems);

    itemTitle.setCellValueFactory(new PropertyValueFactory<>("itemTitle"));
    itemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
  }

  @FXML
  void clearItemBtns(ActionEvent event) {
    for (Button btn : itemButtons) {
      btn.setVisible(false);
    }
  }

  @FXML
  void menuOne(ActionEvent event) throws IOException {
    clearItemBtns(event);
    int menuID = 1;
    int i = 0;

    for (Items item : items) {
      if (item.getMenuID() == menuID) {
        itemButtons.get(i).setText(item.getItemTitle() + "\n" + item.getItemPrice() + "₪");
        itemButtons.get(i).setVisible(true);
        i++;
      }
    }
  }

  @FXML
  void menuTwo(ActionEvent event) throws IOException {
    clearItemBtns(event);
    int menuID = 2;
    int i = 0;

    for (Items item : items) {
      if (item.getMenuID() == menuID) {
        itemButtons.get(i).setText(item.getItemTitle() + "\n" + item.getItemPrice() + "₪");
        itemButtons.get(i).setVisible(true);
        i++;
      }
    }
  }

  @FXML
  void menuThree(ActionEvent event) throws IOException {
    clearItemBtns(event);
    int menuID = 3;
    int i = 0;

    for (Items item : items) {
      if (item.getMenuID() == menuID) {
        itemButtons.get(i).setText(item.getItemTitle() + "\n" + item.getItemPrice() + "₪");
        itemButtons.get(i).setVisible(true);
        i++;
      }
    }
  }

  @FXML
  void menuFour(ActionEvent event) throws IOException {
    clearItemBtns(event);
    int menuID = 4;
    int i = 0;

    for (Items item : items) {
      if (item.getMenuID() == menuID) {
        itemButtons.get(i).setText(item.getItemTitle() + "\n" + item.getItemPrice() + "₪");
        itemButtons.get(i).setVisible(true);
        i++;
      }
    }
  }

  @FXML
  void itemBtnPressed(ActionEvent event) {
    Button button = (Button) event.getSource();
    String buttonText = button.getText();
    String[] parts = buttonText.split("\n");
    String itemName = parts[0];

    for (Items item : items) {
      if (item.getItemTitle().equals(itemName)) {
        orderedItems.add(item);
        break;
      }
    }
    updateTotalPrice();
  }

  private void updateTotalPrice() {
    double totalsum = 0.0;
    // Sum up the prices of all items in the orderedItems list
    for (Items item : orderedItems) {
      totalsum += item.getItemPrice();
    }
    // Set the total price text
    totalPrice.setText(String.format("%.2f", totalsum) + "₪");
  }

  @FXML
  void clear(ActionEvent event) {
    name.clear();
    phone.clear();
    address.clear();
    orderType.setText("Order Type");
    paymentMethod.setText("Payment Method");
    orderedItems.clear();
    updateTotalPrice();
  }

  @FXML
  private void selectMethod(ActionEvent event) {
    MenuItem menuItem = (MenuItem) event.getSource();
    String selected = menuItem.getText();
    System.out.println(selected);
    paymentMethod.setText(selected);
  }

  @FXML
  private void selectType(ActionEvent event) {
    MenuItem menuItem = (MenuItem) event.getSource();
    String selected = menuItem.getText();
    orderType.setText(selected);
  }

  @FXML
  private void logOut(ActionEvent event) throws IOException {
    DB_Main.setRoot("login");
  }

  @FXML
  private void pay(ActionEvent event) throws IOException, ClassNotFoundException {
    // Get the current date
    Date date = new Date();

    // Get the order type
    String orderType = this.orderType.getText();

    // Get the payment method
    String paymentMethod = this.paymentMethod.getText();

    // Get the customer name
    String customerName = name.getText();

    // Get the customer phone number
    String customerPhone = phone.getText();

    // Get the customer address
    String customerAddress = address.getText();

    // Get the total price
    double totalPrice = Double.parseDouble(this.totalPrice.getText().split("₪")[0]);

    // Get the current user name
    String employeeName = UserName.getText();

    // Add the order to the database
    try {
      PreparedStatement statement = Connector.con.connectDB().prepareStatement(
          "INSERT INTO orders (order_date, orderType, paymentMethod, customerName, phoneNumber, address, totalPrice, employeeName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
      statement.setDate(1, new java.sql.Date(date.getTime()));
      statement.setString(2, orderType);
      statement.setString(3, paymentMethod);
      statement.setString(4, customerName);
      statement.setString(5, customerPhone);
      statement.setString(6, customerAddress);
      statement.setDouble(7, totalPrice);
      statement.setString(8, employeeName);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Get the order ID
    int orderID = 0;
    try {
      PreparedStatement statement = Connector.con.connectDB().prepareStatement(
          "SELECT orderID FROM orders WHERE order_date = ? AND orderType = ? AND paymentMethod = ? AND customerName = ? AND phoneNumber = ? AND address = ? AND totalPrice = ? AND employeeName = ?");
      statement.setDate(1, new java.sql.Date(date.getTime()));
      statement.setString(2, orderType);
      statement.setString(3, paymentMethod);
      statement.setString(4, customerName);
      statement.setString(5, customerPhone);
      statement.setString(6, customerAddress);
      statement.setDouble(7, totalPrice);
      statement.setString(8, employeeName);
      statement.executeQuery();
      orderID = statement.getResultSet().getInt("orderID");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Create a new order
    Order order = new Order(orderID, date, paymentMethod, totalPrice, orderType, customerPhone, customerAddress,
        employeeName, customerName);

    clear(event);
  }

  @FXML
  private void switchToCash(ActionEvent event) throws IOException {
    DB_Main.setRoot("cash");
  }

  @FXML
  private void switchToOrders(ActionEvent event) throws IOException {
    DB_Main.setRoot("orders");
  }

  @FXML
  private void switchToInventory(ActionEvent event) throws IOException {
    DB_Main.setRoot("inventory");
  }

  @FXML
  private void switchToEmployees(ActionEvent event) throws IOException {
    DB_Main.setRoot("employees");
  }
}
