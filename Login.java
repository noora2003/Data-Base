package com.db;

import java.util.Date;

public class Login {
  private int loginID;
  private Date loginDate;
  private int userID;

  public Login() {

  }

  public Login(int loginID, Date loginDate, int userID) {
    this.loginID = loginID;
    this.loginDate = loginDate;
    this.userID = userID;
  }

  public int getloginID() {
    return loginID;
  }

  public void setloginID(int loginID) {
    this.loginID = loginID;
  }

  public Date getLoginDate() {
    return loginDate;
  }

  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  @Override
  public String toString() {
    return "Login{" +
        "loginID=" + loginID +
        ", loginDate=" + loginDate +
        ", userID=" + userID +
        '}';
  }

}
