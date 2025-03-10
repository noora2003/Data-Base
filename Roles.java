package com.db;

public class Roles {
  private int roleID;
  private String roleTitle;

  public Roles() {
  }

  public Roles(int roleID, String roleTitle) {
    this.roleID = roleID;
    this.roleTitle = roleTitle;
  }

  public int getRoleID() {
    return roleID;
  }

  public void setRoleID(int roleID) {
    this.roleID = roleID;
  }

  public String getRoleTitle() {
    return roleTitle;
  }

  public void setRoleTitle(String roleTitle) {
    this.roleTitle = roleTitle;
  }

  @Override
  public String toString() {
    return "roles{" +
        "roleID=" + roleID +
        ", roleTitle='" + roleTitle + '\'' +
        '}';
  }

}
