package com.db;

public class Menus {
  private int menuID;
    private String menuTitle;

    public Menus(){}

    public Menus(int menuID, String menuTitle) {
        this.menuID = menuID;
        this.menuTitle = menuTitle;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    @Override
    public String toString() {
        return "Menus{" +
                "menuID=" + menuID +
                ", menuTitle='" + menuTitle + '\'' +
                '}';
    }
}
