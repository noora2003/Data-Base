package com.db;

public class Items {
  private int itemID;
  private String itemTitle;
  private double itemPrice;
  private int menuID;

  public Items() {
  }

  public Items(int itemID, String itemTitle, double itemPrice, int menuID) {
    this.itemID = itemID;
    this.itemTitle = itemTitle;
    this.itemPrice = itemPrice;
    this.menuID = menuID;
  }

  public int getItemID() {
    return itemID;
  }

  public void setItemID(int itemID) {
    this.itemID = itemID;
  }

  public String getItemTitle() {
    return itemTitle;
  }

  public void setItemTitle(String itemTitle) {
    this.itemTitle = itemTitle;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(double itemPrice) {
    this.itemPrice = itemPrice;
  }

  public int getMenuID() {
    return menuID;
  }

  public void setMenuID(int menuID) {
    this.menuID = menuID;
  }

  @Override
  public String toString() {
    return "Items{" +
        "itemID=" + itemID +
        ", itemTitle='" + itemTitle + '\'' +
        ", itemPrice=" + itemPrice +
        ", menuID=" + menuID +
        '}';
  }

}
