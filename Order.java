package com.db;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import java.util.Date;

public class Order {
  private final IntegerProperty orderID = new SimpleIntegerProperty();
  private final Date date;
  private final StringProperty payment = new SimpleStringProperty();
  private final DoubleProperty totalPrice = new SimpleDoubleProperty();
  private final StringProperty type = new SimpleStringProperty();
  private final StringProperty phoneNumber = new SimpleStringProperty();
  private final StringProperty address = new SimpleStringProperty();
  private final StringProperty employeeName = new SimpleStringProperty();
  private final StringProperty customerName = new SimpleStringProperty();

  public Order() {
    this(0, new Date(), "", 0.0, "", "", "", "", "");
  }

  public Order(int orderID, Date date, String payment, double totalPrice, String type, String phoneNumber,
      String address, String employeeName, String customerName) {
    this.orderID.set(orderID);
    this.date = date;
    this.payment.set(payment);
    this.totalPrice.set(totalPrice);
    this.type.set(type);
    this.phoneNumber.set(phoneNumber);
    this.address.set(address);
    this.employeeName.set(employeeName);
    this.customerName.set(customerName);
  }

  public int getOrderID() {
    return orderID.get();
  }

  public IntegerProperty getOrderIdProperty() {
    return orderID;
  }

  public void setOrderId(int orderId) {
    this.orderID.set(orderId);
  }

  // set date
  public void setDate(Date date) {
    this.date.setTime(date.getTime());
  }

  public Date getDate() {
    return date;
  }

  public Date getDateProperty() {
    return date;
  }

  public String getPayment() {
    return payment.get();
  }

  public StringProperty getPaymentProperty() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment.set(payment);
  }

  public Double getTotalPrice() {
    return totalPrice.get();
  }

  public DoubleProperty getTotalPriceProperty() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice.set(totalPrice);
  }

  public String getType() {
    return type.get();
  }

  public StringProperty getTypeProperty() {
    return type;
  }

  public void setType(String type) {
    this.type.set(type);
  }

  public String getAddress() {
    return address.get();
  }

  public StringProperty getAddressProperty() {
    return address;
  }

  public void setAddress(String address) {
    this.address.set(address);
  }

  public String getPhoneNumber() {
    return phoneNumber.get();
  }

  public StringProperty getPhoneNumberProperty() {
    return phoneNumber;
  }

  public void setPhoneNumber(String PhoneNumber) {
    this.phoneNumber.set(PhoneNumber);
  }

  public String getEmployeeName() {
    return employeeName.get();
  }

  public StringProperty getEmployeeNameProperty() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName.set(employeeName);
  }

  public String getCustomerName() {
    return customerName.get();
  }

  public StringProperty getCustomerNameProperty() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName.set(customerName);
  }

}
