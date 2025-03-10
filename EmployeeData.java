package com.db;


public class EmployeeData {
  private int  EmployeeID;
  private String FullName;
  private String Email;
  private String PhoneNumber;
  private int BranchID;
  private int RoleID;
  private int ScheduleID;

  private double Salary;

  public EmployeeData(int  EmployeeID, String  FullName, String Email, String PhoneNumber, int BranchID, int RoleID, int ScheduleID,double Salary) {
    this. EmployeeID= EmployeeID;
    this. FullName= FullName;
    this.Email = Email;
    this.PhoneNumber = PhoneNumber;
    this.BranchID = BranchID;
    this.RoleID = RoleID;
    this.ScheduleID=ScheduleID;

    this.Salary = Salary;
  }

  public int getEmployeeID(){
    return  EmployeeID;
  }

  public String getFullName() {
    return  FullName;
  }

  public String getEmail() {
    return Email;
  }

  public String getPhoneNumber() {
    return PhoneNumber;
  }

  public int getBranchID() {
    return BranchID;
  }

  public int getRoleID() {
    return RoleID;
  }
  public int getScheduleID() {
    return ScheduleID;
  }


  public double getSalary() {
    return Salary;
  }




}
