package com.db;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static com.db.DB_Main.roles;
import static com.db.DB_Main.users;

public class createAccountController {

  @FXML
  private ToggleGroup accessLevel;

  @FXML
  private RadioButton adminAL;

  @FXML
  private RadioButton employeeAL;

  @FXML
  private MenuButton roleMenuButton;

  @FXML
  private Button createAccountBtn;

  @FXML
  private Button back;

  @FXML
  private TextField tvPassword;

  @FXML
  private TextField tvUsername;

  @FXML
  private void selectRole(ActionEvent event) {
    MenuItem menuItem = (MenuItem) event.getSource();
    String selectedRole = menuItem.getText();
    roleMenuButton.setText(selectedRole);
  }

  @FXML
  void createAccount(ActionEvent event) throws SQLException, ClassNotFoundException {
    System.out.println("ello\n");

    int accessLevel = 0;

    if (roleMenuButton.getText().equals("Manager")) {
      accessLevel = 1;
    } else if (roleMenuButton.getText().equals("Employee")) {
      accessLevel = 2;
    }

    Users tmp = new Users(tvUsername.getText(), tvPassword.getText(), accessLevel);

    // check the password:
    int check = 0;

    for (Users users : users) {
      if ((tmp.getPassword().equals(users.getPassword()))) {
        check = 1;
        break;
      }
    }
    System.out.println(check);

    // make sure that role id exist:
    int flag = 0;
    for (Roles roles : roles) {
      System.out.println("roles\n");
      System.out.println(tmp.getUserRoleID() + "and" + roles.getRoleID());
      if (tmp.getUserRoleID() == roles.getRoleID()) {
        flag = 1;
        System.out.println("flag? \n" + flag);
        if (check == 0) {
          // check=1;
          users.add(tmp);// you added with out id, so re read from data base
          // you have to add the user to the database

          // updating database
          String SQL = "insert into Users(userName,userPassword,userRoleId) values('"
              + tvUsername.getText() +
              "','" + tvPassword.getText() +
              "'," + accessLevel + ");";

          System.out.println(SQL);

          Connector.con.connectDB();
          Statement stmt = Connector.con.connectDB().createStatement();
          stmt.executeUpdate(SQL);
          stmt.close();
          Connector.con.connectDB().close();
          tvUsername.clear();
          tvPassword.clear();
          roleMenuButton.setText("");
          users.clear();
          System.out.println("done\n");
          break;
        }
      }
    }
    System.out.println("done2\n");
  }

  @FXML
  void goBack(ActionEvent event) throws IOException {
    DB_Main.setRoot("login");
  }

}