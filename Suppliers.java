package com.db;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;

public class Suppliers {
    private final IntegerProperty supplierID = new SimpleIntegerProperty();
    private final StringProperty supplierName = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final IntegerProperty phoneNumber = new SimpleIntegerProperty();

    public Suppliers() {
    }

    public Suppliers (int supplierID, String supplierName, String email,int phoneNumber) {
        this.supplierID.set(supplierID);
        this.supplierName.set(supplierName);
        this.email.set(email);
        this.phoneNumber.set(phoneNumber);
    }

    public Suppliers(TableColumn<Suppliers, Integer> supplierID, String supplierName, String email, int phoneNumber) {
    }

    public int getSupplierID() { return supplierID.get();}

    public IntegerProperty getSupplierIDProperty() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID.set(supplierID);
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty getSupplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }


    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public IntegerProperty getPhoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}

  
