package com.db;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Complaints {
  private final IntegerProperty complaintID = new SimpleIntegerProperty();
  private final IntegerProperty customerID = new SimpleIntegerProperty();
  private final StringProperty date = new SimpleStringProperty();
  private final StringProperty description = new SimpleStringProperty();


  public Complaints() {
  }

  public Complaints(int complaintID, int customerID, String date, String description) {
    this.complaintID.set(complaintID);
    this.customerID.set(customerID);
    this.date.set(date);
    this.description.set(description);
   
  }

  public int getComplaintID() {
    return complaintID.get();
  }

  public IntegerProperty getComplaintIDProperty() {
    return complaintID;
  }

  public void setComplaintID(int complaintID) {
    this.complaintID.set(complaintID);
  }
  public int getCustomerID() {
    return customerID.get();
  }

  public IntegerProperty getCustomerIDProperty() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID.set(customerID);
  }
  public String getDate() {
    return date.get();
  }

  public StringProperty getDateProperty() {
    return date;
  }

  public void setDate(String date) {
    this.date.set(date);
  }

  public String getDescription() {
    return description.get();
  }

  public StringProperty getDescriptionProperty() {
    return description;
  }

  public void setDiscreption(String description) {
    this.description.set(description);
  }
}
