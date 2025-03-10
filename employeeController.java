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


import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class employeeController {

    @FXML
    private Button add_emp_btn;

    @FXML
    private TableColumn<EmployeeData, String> branch_emp;

    @FXML
    private TextField branch_textfeild;

    @FXML
    private Button clear_emp_btn;

    @FXML
    private Button delete_emp_btn;

    @FXML
    private TableColumn<EmployeeData, String> email_emp;

    @FXML
    private TextField email_textfeild;

    @FXML
    private AnchorPane emp_pane;

    @FXML
    private TableView<EmployeeData> emp_table;

    @FXML
    private TableColumn<EmployeeData, String> id_emp;

    @FXML
    private TextField id_textfeild;

    @FXML
    private TableColumn<EmployeeData, String> name_emp;

    @FXML
    private TextField name_textfeild;

    @FXML
    private TableColumn<EmployeeData, String> phone_emp;

    @FXML
    private TextField phone_textfeild;

    @FXML
    private TableColumn<EmployeeData, String> role_emp;

    @FXML
    private TextField role_textfeild;

    @FXML
    private TableColumn<EmployeeData, Double> salary_emp;

    @FXML
    private TextField salary_textfeild;

    @FXML
    private TableColumn<EmployeeData, String> schedule_emp;

    @FXML
    private TextField schedule_textfeild;

    @FXML
    private TextField search_emp;

    @FXML
    private Button update_emp_btn;


    private java.sql.Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;


    public ObservableList<EmployeeData> EmployeeListData() {

        ObservableList<EmployeeData> LiatData = FXCollections.observableArrayList();
        String sql = "Select * from Employee";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);

            result = prepare.executeQuery();
            EmployeeData employeeD;
            while (result.next()) {
                employeeD = new EmployeeData(result.getInt("EmployeeID"),
                        result.getString("FullName"),
                        result.getString("Email"),
                        result.getString("PhoneNumber"),
                        result.getInt("BranchID"),
                        result.getInt("RoleID"),
                        result.getInt("ScheduleID"),
                        result.getDouble("Salary"));
                LiatData.add(employeeD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return LiatData;
    }

    private ObservableList<EmployeeData> EmployeeList;

    public void addEmployeeShowListData() {
        EmployeeList = EmployeeListData();
        id_emp.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        name_emp.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        email_emp.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phone_emp.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        branch_emp.setCellValueFactory(new PropertyValueFactory<>("BranchID"));
        schedule_emp.setCellValueFactory(new PropertyValueFactory<>("ScheduleID"));
        role_emp.setCellValueFactory(new PropertyValueFactory<>("RoleID"));
        salary_emp.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        emp_table.setItems(EmployeeList);

    }

    public void addEmployeeSelect() {

        EmployeeData employeeD = emp_table.getSelectionModel().getSelectedItem();
        int num = emp_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        id_textfeild.setText(String.valueOf(employeeD.getEmployeeID()));
        name_textfeild.setText(employeeD.getFullName());
        email_textfeild.setText(employeeD.getEmail());
        schedule_textfeild.setText(String.valueOf(employeeD.getScheduleID()));
        phone_textfeild.setText(employeeD.getPhoneNumber());
        branch_textfeild.setText(String.valueOf(employeeD.getBranchID()));
        role_textfeild.setText(String.valueOf(employeeD.getRoleID()));
        salary_textfeild.setText(String.valueOf(employeeD.getSalary()));

    }

    public void addEmployeeAdd() {

        String sql = "INSERT INTO Employee "
                + "(EmployeeID,FullName,Email,PhoneNumber,BranchID, RoleID, ScheduleID,Salary) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || name_textfeild.getText().isEmpty()
                    || email_textfeild.getText().isEmpty()
                    || schedule_textfeild.getText().isEmpty()
                    || phone_textfeild.getText().isEmpty()
                    || branch_textfeild.getText().isEmpty()
                    || role_textfeild.getText().isEmpty()
                    || salary_textfeild.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                String check = "SELECT  EmployeeID FROM Employee WHERE  EmployeeID = '"
                        + id_textfeild.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("EmployeeID: " + id_textfeild.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, id_textfeild.getText());
                    prepare.setString(2, name_textfeild.getText());
                    prepare.setString(3, email_textfeild.getText());
                    prepare.setString(4, phone_textfeild.getText());
                    prepare.setInt(5, Integer.parseInt(branch_textfeild.getText()));
                    prepare.setInt(6, Integer.parseInt(role_textfeild.getText()));
                    prepare.setInt(7, Integer.parseInt(schedule_textfeild.getText()));
                    prepare.setBigDecimal(8, new BigDecimal(salary_textfeild.getText()));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    addEmployeeShowListData();
                    addEmployeeReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeReset() {
        id_textfeild.setText("");
        name_textfeild.setText("");
        email_textfeild.setText("");
        schedule_textfeild.setText("");
        phone_textfeild.setText("");
        branch_textfeild.setText("");
        role_textfeild.setText("");
        salary_textfeild.setText("");

    }

    public void addEmployeeUpdate() {
        String sql = "UPDATE Employee SET EmployeeID = '"
                + id_textfeild.getText() + "', FullName = '"
                + name_textfeild.getText() + "', Email = '"
                + email_textfeild.getText() + "', PhoneNumber = '"
                + phone_textfeild.getText() + "', BranchID = '"
                + branch_textfeild.getText() + "', RoleID = '"
                + role_textfeild.getText() + "', ScheduleID ='"
                + schedule_textfeild.getText() + "', Salary ='"
                + salary_textfeild.getText() + "' WHERE EmployeeID ='"
                + id_textfeild.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || name_textfeild.getText().isEmpty()
                    || email_textfeild.getText().isEmpty()
                    || schedule_textfeild.getText().isEmpty()
                    || phone_textfeild.getText().isEmpty()
                    || branch_textfeild.getText().isEmpty() ///|| getData.path == null || getData.path == ""    )
                    || role_textfeild.getText().isEmpty()
                    || salary_textfeild.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE EmployeeID: " + id_textfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("\"Successfully Updated!\"");
                    alert.showAndWait();
                    addEmployeeShowListData();
                    addEmployeeReset();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeDelete() {

        String sql = "DELETE FROM Employee WHERE EmployeeID = '"
                + id_textfeild.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (id_textfeild.getText().isEmpty()
                    || name_textfeild.getText().isEmpty()
                    || email_textfeild.getText().isEmpty()
                    || schedule_textfeild.getText().isEmpty()
                    || phone_textfeild.getText().isEmpty()
                    || branch_textfeild.getText().isEmpty()
                    || role_textfeild.getText().isEmpty()
                    || salary_textfeild.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE EmployeeID: " + id_textfeild.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("\"Successfully DELETE!\"");
                    alert.showAndWait();
                    addEmployeeShowListData();
                    addEmployeeReset();


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addEmployeeSearch() {

        String searchName = search_emp.getText();

        try {
            String query = "SELECT * FROM Employee WHERE FullName LIKE ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<EmployeeData> searchResults = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("EmployeeID");
                String name = resultSet.getString("FullName");
                String email = resultSet.getString("Email");
                String phoneNumber = resultSet.getString("PhoneNumber");
                int branch = resultSet.getInt("BranchID");
                int role = resultSet.getInt("RoleID");
                double salary = resultSet.getDouble("Salary");
                int schedule = resultSet.getInt("ScheduleID");


                EmployeeData employee = new EmployeeData(id, name, email, phoneNumber, branch, role, schedule, salary);
                searchResults.add(employee);
            }

            ObservableList<EmployeeData> observableList = FXCollections.observableArrayList(searchResults);
            emp_table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//    SortedList<EmployeeData> sortList = new SortedList<>(filter);
//    sortList.comparatorProperty().bind(emp_table.comparatorProperty());
//    emp_table.setItems(sortList);
    }

    public void createEmployeeTable() {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Employee (EmployeeID INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + " FullName TEXT, Email TEXT, PhoneNumber TEXT, BranchID INTEGER, RoleID  INTEGER, ScheduleID INTEGER ,Salary DOUBLE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resource) {
        addEmployeeShowListData();
    }

}