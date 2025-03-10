package com.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static com.db.DB_Main.loginList;
import static com.db.DB_Main.users;

public class loginController {

  public static int currentUserID = 0;
  public static int currentUserRole = 0;
  public static String currentUsername = "";

  public static ObservableList<com.db.Login> logins = FXCollections.observableArrayList(loginList);

  @FXML
  private Button loginBtn;

  @FXML
  private Text loginDetection;

  @FXML
  private PasswordField tvPassword;

  @FXML
  private TextField tvUsername;
  private static int loginIDAutoIncreament = 0;
  Login tm;

  @FXML
  void Login(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {

    // loginDetection.setDisable(true);
    // loginDetection.setVisible(false);

    String tmpName = tvUsername.getText();
    String tmpPassword = tvPassword.getText();
    int tmpUserID = 0;
    int flag = 0;

    // searching at database
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getUserName().equals(tmpName) && users.get(i).getPassword().equals(tmpPassword)) {
        flag = 1;
        if (loginList.size() != 0) {
          loginIDAutoIncreament = loginList.size();
        }
        loginIDAutoIncreament++;

        currentUserID = users.get(i).getUserID();
        currentUserRole = users.get(i).getUserRoleID();
        currentUsername = users.get(i).getUserName();

        if (currentUserRole == 1)
          DB_Main.setRoot("mainMenuManager");
        else if (currentUserRole == 2)
          DB_Main.setRoot("mainMenuEmployee");
        
        System.out.println("Login successful\n");

        for (Users users : users) {
          if (tmpName.equals(users.getUserName()))
            tmpUserID = users.getUserID();
        }

        // sending log in data to database
        tm = new Login(loginIDAutoIncreament, new Date(), tmpUserID);// here we need use id
        loginList.add(tm);

        String s = String.valueOf(
            tm.getLoginDate().getYear() + '-' + tm.getLoginDate().getMonth() + '-' + tm.getLoginDate().getDay());
        String SQL = "insert into login values(" + loginIDAutoIncreament + "," + s + "," + tm.getUserID() + ");";

        Connector.con.connectDB();
        PreparedStatement preparedStatement = Connector.con.connectDB().prepareStatement(SQL);
        preparedStatement.execute();
        preparedStatement.close();
        Connector.con.connectDB().close();
        break;

      }
    }
    if (flag == 0) {
      loginDetection.setVisible(true);
      loginDetection.setText("invalid user name or password");
    }

  }

  @FXML
  void switchToCreate(ActionEvent event) throws IOException {
    DB_Main.setRoot("createAccount");
  }
}
