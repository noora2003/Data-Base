package com.db;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.io.IOException;

public class mainMenuEmployeeController {

  @FXML
  private Text welcome;

  @FXML
  public void initialize() {
    welcome.setText("Welcome, " + loginController.currentUsername + "!");
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
  private void switchToMenuitems(ActionEvent event) throws IOException {
    DB_Main.setRoot("menuitems");
  }

  @FXML
  private void logOut(ActionEvent event) throws IOException {
    DB_Main.setRoot("login");
  }

}
