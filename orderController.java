package com.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class orderController {

    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, Integer> orderID, phone;

    @FXML
    private TableColumn<Order, String> orderType, method, address, custName, userName;

    @FXML
    private TableColumn<Order, Double> price;

    @FXML
    private TableColumn<Order, Date> date;

    @FXML
    private TextField tvID, tvType, tvPrice, tvMethod, tvAddress, tvPhone, tvCustName, tvUserName;

    // date picker
    @FXML
    private DatePicker tvDate;

    @FXML
    private Text dayOrd, weekOrd, monthOrd, yearOrd, profit;

    private Connection connection;

    private ObservableList<Order> ordersList = FXCollections.observableArrayList();

    @FXML
    void initialize() throws ClassNotFoundException {
        tableView.setItems(ordersList);

        // Configure table columns
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderType.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        method.setCellValueFactory(new PropertyValueFactory<>("payment"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        // Load orders data into the table
        loadOrdersData();

    }

    @FXML
    private void loadOrdersData() throws ClassNotFoundException {
        try {

            Statement statement = Connector.con.connectDB().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

            // Clear existing data from the table
            tableView.getItems().clear();

            // Populate the table with data from the database
            while (resultSet.next()) {
                int id = resultSet.getInt("orderID");
                Date orderDate = resultSet.getDate("order_date");
                System.out.println(orderDate);
                String paymentMethod = resultSet.getString("paymentMethod");
                double totalPrice = resultSet.getDouble("totalPrice");
                String type = resultSet.getString("orderType");
                String phoneNumber = resultSet.getString("phoneNumber");
                String orderAddress = resultSet.getString("address");
                String customerName = resultSet.getString("customerName");
                String employeeName = resultSet.getString("employeeName");

                // Create an Order object and add it to the table
                tableView.getItems()
                        .add(new Order(id, orderDate, paymentMethod, totalPrice, type, phoneNumber, orderAddress,
                                employeeName, customerName));
            }
            // Calculate and display the number of orders made during different time periods
            calculateOrdersCount();
            dailySum();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calculateOrdersCount() throws ClassNotFoundException {
        try {
            // Get current date
            LocalDate currentDate = LocalDate.now();

            // Calculate dates for last week, month, and year
            LocalDate lastWeekDate = currentDate.minus(7, ChronoUnit.DAYS);
            LocalDate lastMonthDate = currentDate.minus(1, ChronoUnit.MONTHS);
            LocalDate lastYearDate = currentDate.minus(1, ChronoUnit.YEARS);

            // Calculate number of orders for each time period
            int todayOrders = countOrders(currentDate);
            int weekOrders = countOrders(lastWeekDate);
            int monthOrders = countOrders(lastMonthDate);
            int yearOrders = countOrders(lastYearDate);

            // Update the UI with the calculated orders count
            dayOrd.setText(String.valueOf(todayOrders));
            weekOrd.setText(String.valueOf(weekOrders));
            monthOrd.setText(String.valueOf(monthOrders));
            yearOrd.setText(String.valueOf(yearOrders));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        tvID.clear();
        tvType.clear();
        tvPrice.clear();
        tvMethod.clear();
        tvAddress.clear();
        tvPhone.clear();
        tvDate.setValue(null);
        tvCustName.clear();
        tvUserName.clear();
    }

    private int countOrders(LocalDate date) throws SQLException, ClassNotFoundException {
        // Prepare SQL query to count orders for a specific date
        String sql = "SELECT COUNT(*) FROM orders WHERE order_date >= ?";
        PreparedStatement statement = Connector.con.connectDB().prepareStatement(sql);
        statement.setDate(1, Date.valueOf(date));

        // Execute query and get the result
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @FXML
    private void dailySum() {
        try {
            LocalDate currentDate = LocalDate.now();

            // Prepare SQL query to select orders made today
            String query = "SELECT SUM(totalPrice) FROM orders WHERE order_date = ?";
            PreparedStatement statement = Connector.con.connectDB().prepareStatement(query);
            statement.setDate(1, Date.valueOf(currentDate));

            // Execute the query and get the sum of total prices
            ResultSet resultSet = statement.executeQuery();
            double sum = 0;
            if (resultSet.next()) {
                sum = resultSet.getDouble(1);
            }

            // Set the sum as the text for the "profit" Text field
            profit.setText(String.valueOf(sum));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        DB_Main.setRoot("login");
    }

    @FXML
    private void switchToCash(ActionEvent event) throws IOException {
        DB_Main.setRoot("cash");
    }

    @FXML
    private void addOrder(ActionEvent event) throws SQLException, ClassNotFoundException {

        {

            System.out.println("Adding Order");
            int id = Integer.parseInt(tvID.getText());
            String type = tvType.getText();
            double price = Double.parseDouble(tvPrice.getText());
            String method = tvMethod.getText();
            String address = tvAddress.getText();
            String phone = tvPhone.getText();
            // date of datepicker
            Date date = Date.valueOf(tvDate.getValue());
            String name = tvCustName.getText();
            String userName = tvUserName.getText();

            Order newOrder = new Order(id, Date.valueOf(LocalDate.now()), method, price, type, phone, address, userName,
                    name);

            ordersList.add(newOrder);

            // Clear existing data from the table
            tableView.getItems().clear();

            String query = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = Connector.con.connectDB().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, type);
            pstmt.setDouble(3, price);
            pstmt.setString(4, method);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setDate(7, date);
            pstmt.setString(8, name);
            pstmt.setString(9, userName);
            pstmt.executeUpdate();

            clear(event);
            loadOrdersData();
        }

    }

    @FXML
    private void updateOrder(ActionEvent event) throws SQLException, ClassNotFoundException {

        {
            System.out.println("Updating Order");
            int id = Integer.parseInt(tvID.getText());
            String type = tvType.getText();
            double price = Double.parseDouble(tvPrice.getText());
            String method = tvMethod.getText();
            String address = tvAddress.getText();
            String phone = tvPhone.getText();
            // date of datepicker
            Date date = Date.valueOf(tvDate.getValue());
            String name = tvCustName.getText();
            String userName = tvUserName.getText();

            // Clear existing data from the table
            tableView.getItems().clear();

            String query = "UPDATE orders SET orderType = ?, totalPrice = ?, paymentMethod = ?," +
                    " address = ?, phoneNumber = ?, order_date = ?, customerName = ?, employeeName = ? WHERE orderID = ?";
            PreparedStatement pstmt = Connector.con.connectDB().prepareStatement(query);
            pstmt.setString(1, type);
            pstmt.setDouble(2, price);
            pstmt.setString(3, method);
            pstmt.setString(4, address);
            pstmt.setString(5, phone);
            pstmt.setDate(6, date);
            pstmt.setString(7, name);
            pstmt.setString(8, userName);
            pstmt.setInt(9, id);
            pstmt.executeUpdate();

            clear(event);
            loadOrdersData();
        }

    }

    @FXML
    private void deleteOrder(ActionEvent event) throws SQLException, ClassNotFoundException {

        {
            System.out.println("Deleting Order");
            int id = Integer.parseInt(tvID.getText());

            // Clear existing data from the table
            tableView.getItems().clear();

            String query = "DELETE FROM orders WHERE orderID = ?";
            PreparedStatement pstmt = Connector.con.connectDB().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            clear(event);
            loadOrdersData();
        }

    }

    @FXML
    private void searchOrder(ActionEvent event) {
        try {
            String id = tvID.getText();
            String type = tvType.getText();
            String price = tvPrice.getText();
            String method = tvMethod.getText();
            String address = tvAddress.getText();
            String phone = tvPhone.getText();
            Date date = null;
            if (tvDate.getValue() != null) {
                date = Date.valueOf(tvDate.getValue());
            }
            String custName = tvCustName.getText();
            String userName = tvUserName.getText();

            // Construct the SQL query based on the provided parameters
            String query = "SELECT * FROM orders WHERE";
            boolean isFirstCondition = true;

            if (!id.isEmpty()) {
                query += " orderID = '" + id + "'";
                isFirstCondition = false;
            }
            if (!type.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " orderType = '" + type + "'";
                isFirstCondition = false;
            }
            if (!price.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " totalPrice = '" + price + "'";
                isFirstCondition = false;
            }
            // Add conditions for other properties similarly
            if (!method.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " paymentMethod = '" + method + "'";
                isFirstCondition = false;
            }
            if (!address.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " address = '" + address + "'";
                isFirstCondition = false;
            }
            if (!phone.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " phoneNumber = '" + phone + "'";
                isFirstCondition = false;
            }
            if (date != null) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " order_date = '" + date + "'";
                isFirstCondition = false;
            }
            if (!custName.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " customerName = '" + custName + "'";
                isFirstCondition = false;
            }
            if (!userName.isEmpty()) {
                if (!isFirstCondition) {
                    query += " AND";
                }
                query += " employeeName = '" + userName + "'";
                isFirstCondition = false;
            }

            // Execute the query
            Statement statement = Connector.con.connectDB().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Clear existing data from the table
            tableView.getItems().clear();

            // Populate the table with data from the database
            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderID");
                Date orderDate = resultSet.getDate("order_date");
                String paymentMethod = resultSet.getString("paymentMethod");
                double totalPrice = resultSet.getDouble("totalPrice");
                String orderType = resultSet.getString("orderType");
                String phoneNumber = resultSet.getString("phoneNumber");
                String orderAddress = resultSet.getString("address");
                String customerName = resultSet.getString("customerName");
                String employeeName = resultSet.getString("employeeName");

                // Create an Order object and add it to the table
                tableView.getItems().add(new Order(orderId, orderDate, paymentMethod, totalPrice, orderType,
                        phoneNumber, orderAddress, employeeName, customerName));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        clear(event);
    }

    @FXML
    private void switchToInventory(ActionEvent event) throws IOException {
        DB_Main.setRoot("Inventory");
    }

    @FXML
    private void switchToEmployees(ActionEvent event) throws IOException {
        DB_Main.setRoot("employees");
    }
}