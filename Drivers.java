package com.db;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;

public class Drivers {
    private final IntegerProperty driverID = new SimpleIntegerProperty();
    private final StringProperty driverName = new SimpleStringProperty();
    private final IntegerProperty licenseNumber = new SimpleIntegerProperty();
    private final IntegerProperty phoneNumber = new SimpleIntegerProperty();

    public Drivers() {
    }

    public Drivers (int driverID, String driverName, int licenseNumber, int phoneNumber) {
        this.driverID.set(driverID);
        this.driverName.set(driverName);
        this.licenseNumber.set(licenseNumber);
        this.phoneNumber.set(phoneNumber);
    }

    public Drivers(TableColumn<Drivers, Integer> driverID, String driverName, Integer licenseNumber, Integer phoneNumber ) {
    }

    public int getDriverID() { return driverID.get();}

    public IntegerProperty getDriverIDProperty() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID.set(driverID);
    }

    public String getDriverName() {
        return driverName.get();
    }

    public StringProperty getDriverNameProperty() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName.set(driverName);
    }

    public int getLicenseNumber() {
        return licenseNumber.get();
    }

    public IntegerProperty getLicenseNumberProperty() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber.set(licenseNumber);
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

  
 