package com.db;

public class Users {
  private int userID;
  private String userName;
  private String password;
  private int userRoleID;

  public Users(int userID, String userName, String password, int userRole) {
    this.userID = userID;
    this.userName = userName;
    this.password = password;
    this.userRoleID = userRole;
  }

  public Users(String userName, String password, int userRole) {
    this.userName = userName;
    this.password = password;
    this.userRoleID = userRole;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getUserRoleID() {
    return userRoleID;
  }

  public void setUserRoleID(int userRoleID) {
    this.userRoleID = userRoleID;
  }

  @Override
  public String toString() {
    return "Users{" +
        "ID=" + userID +
        ", name='" + userName + '\'' +
        ", password='" + password + '\'' +
        ", RoleID=" + userRoleID +
        '}';
  }

}
