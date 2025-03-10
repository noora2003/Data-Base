package com.db;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class  DBMenuitemsOperations {

    private Connection con;

    public DBMenuitemsOperations(Connection con) {
        this.con = con;
    }

    // Get all menu items
    public List<Menuitems> getMenuitems() throws SQLException {
        List<Menuitems> Menuitems = new ArrayList<>(); // Corrected variable name to camelCase

        String query = "SELECT * FROM menuitems"; // Ensure this is the correct table name
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int menuID = rs.getInt("menuID");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                String price = rs.getString("price");
                int itemID = rs.getInt("itemID");

                Menuitems menuItems = new Menuitems(menuID, itemName, description, price, itemID);
                Menuitems.add(menuItems); // Correctly adding the menuItem object to the Menuitems list

            }
        }
        System.out.println(Menuitems); // Moved print statement outside the try block
        return Menuitems;
    }

    // Insert a new menu item
    public void insertMenuitems(int menuID, String itemName, String description, String price, int itemID) throws SQLException {
        String query = "INSERT INTO menuitems (menuID, itemName, description, price, itemID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, menuID);
            pstmt.setString(2, itemName);
            pstmt.setString(3, description);
            pstmt.setString(4, price);
            pstmt.setInt(5, itemID);
            pstmt.executeUpdate();
        }
    }

    // Update menu item information
    public void updateMenuitems(int menuID, String itemName, String description, String price, int itemID)
            throws SQLException {
        String query = "UPDATE menuitems SET itemName = ?, description = ?, price = ? WHERE itemID = ? AND menuID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, itemName);
            pstmt.setString(2, description);
            pstmt.setString(3, price);
            pstmt.setInt(4, itemID);
            pstmt.setInt(5, menuID);
            pstmt.executeUpdate();
        }
    }

   // Delete a menu item by ID
   public void deleteMenuitems(int itemID) throws SQLException {
    String query = "DELETE FROM menuitems WHERE itemID = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, itemID);
        pstmt.executeUpdate();
        }
    }
}
