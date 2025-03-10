package com.db;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;

public class Menuitems {
    private final IntegerProperty menuID = new SimpleIntegerProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty price = new SimpleStringProperty();
    private final IntegerProperty itemID = new SimpleIntegerProperty();

    public Menuitems() {
    }

    public Menuitems (int menuID, String itemName, String description, String price, int itemID) {
        this.menuID.set(menuID);
        this.itemName.set(itemName);
        this.description.set(description);
        this.price.set(price);
        this.itemID.set(itemID);
    }

    public Menuitems(TableColumn<Menuitems, Integer> menuID, String itemName, String description, String price, int itemID) {
    }

    public int getMenuID() { return menuID.get();}

    public IntegerProperty getMenuIDProperty() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID.set(menuID);
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty getItemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty getPriceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public int getItemID() {
        return itemID.get();
    }

    public IntegerProperty getItemIDProperty() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID.set(itemID);
    }
}

  
