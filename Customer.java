package com.db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
  private final IntegerProperty id = new SimpleIntegerProperty();
  private final StringProperty name = new SimpleStringProperty();
  private final StringProperty phone = new SimpleStringProperty();
  private final StringProperty email = new SimpleStringProperty();
  private final IntegerProperty points = new SimpleIntegerProperty();

  public Customer() {
  }

  public Customer(int id, String name, String phone, String email, int points) {
    this.id.set(id);
    this.name.set(name);
    this.phone.set(phone);
    this.email.set(email);
    this.points.set(points);
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty getIdProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public StringProperty getNameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getPhone() {
    return phone.get();
  }

  public StringProperty getPhoneProperty() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone.set(phone);
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

  public int getPoints() {
    return points.get();
  }

  public IntegerProperty getPointsProperty() {
    return points;
  }

  public void setPoints(int points) {
    this.points.set(points);
  }
}
