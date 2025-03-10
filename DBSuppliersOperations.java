package com.db;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class  DBSuppliersOperations {

    private Connection con;

    public DBSuppliersOperations(Connection con) {
        this.con = con;
    }

    // Get all menu items
    public List<Suppliers> getSuppliers() throws SQLException {
        List<Suppliers> Suppliers = new ArrayList<>(); // Corrected variable name to camelCase

        String query = "SELECT * FROM suppliers"; // Ensure this is the correct table name
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int supplierID = rs.getInt("supplierID");
                String supplierName = rs.getString("supplierName");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phoneNumber");

                Suppliers suppliers = new Suppliers(supplierID, supplierName, email,phoneNumber);
                Suppliers.add(suppliers); // Correctly adding the menuItem object to the Suppliers list

            }
        }
        System.out.println(Suppliers); // Moved print statement outside the try block
        return Suppliers;
    }

    // Insert a new menu item
    public void insertSuppliers(int supplierID, String supplierName, String email, int phoneNumber) throws SQLException {
        String query = "INSERT INTO suppliers (supplierID, supplierName, email,phoneNumber) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, supplierID);
            pstmt.setString(2, supplierName);
            pstmt.setString(3, email);
            pstmt.setInt(5, phoneNumber);
            pstmt.executeUpdate();
        }
    }

    // Update menu item information
    public void updateSuppliers(int supplierID, String supplierName, String email, int phoneNumber)
        throws SQLException {
    String query = "UPDATE suppliers SET supplierName = ?, email = ?, phoneNumber = ? WHERE supplierID = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setString(1, supplierName); // corresponds to the first question mark in the query
        pstmt.setString(2, email);        // corresponds to the second question mark in the query
        pstmt.setInt(3, phoneNumber);     // corresponds to the third question mark in the query
        pstmt.setInt(4, supplierID);      // corresponds to the fourth question mark in the query
        pstmt.executeUpdate();
    }
}


   // Delete a menu item by ID
   public void deleteSuppliers(int supplierID) throws SQLException {
    String query = "DELETE FROM suppliers WHERE supplierID = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, supplierID);
        pstmt.executeUpdate();
        }
    }
}
