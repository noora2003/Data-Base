package com.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class  DBDriversOperations {

    private Connection con;

    public DBDriversOperations(Connection con) {
        this.con = con;
    }

    // Get all menu items
    public List<Drivers> getDrivers() throws SQLException {
        List<Drivers> Drivers = new ArrayList<>(); // Corrected variable name to camelCase

        String query = "SELECT * FROM drivers"; // Ensure this is the correct table name
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int driverID = rs.getInt("driverID");
                String driverName = rs.getString("driverName");
                int licenseNumber = rs.getInt("licenseNumber");
                int phoneNumber = rs.getInt("phoneNumber");
                

                Drivers drivers = new Drivers(driverID, driverName, licenseNumber, phoneNumber);
                Drivers.add(drivers); // Correctly adding the menuItem object to the Menuitems list

            }
        }
        System.out.println(Drivers); // Moved print statement outside the try block
        return Drivers;
    }

    // Insert a new menu item
    public void insertDrivers(int driverID, String driverName, int licenseNumber, int phoneNumber ) throws SQLException {
        String query = "INSERT INTO drivers (driverID, driverName, licenseNumber, phoneNumber) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, driverID);
            pstmt.setString(2, driverName);
            pstmt.setInt(3, licenseNumber);
            pstmt.setInt(4, phoneNumber);
            pstmt.executeUpdate();
        }
    }

    // Update menu item information
    public void updateDrivers(int driverID, String driverName, int licenseNumber, int phoneNumber)
            throws SQLException {
        String query = "UPDATE drivers SET driverID = ?, driverName = ?, licenseNumber = ? , phoneNumber = ? ";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, driverID);
            pstmt.setString(2, driverName);
            pstmt.setInt(3, licenseNumber);
            pstmt.setInt(4, phoneNumber);
            pstmt.executeUpdate();
        }
    }

   // Delete a menu item by ID
   public void deleteDrivers(int driverID) throws SQLException {
    String query = "DELETE FROM drivers WHERE driverID = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, driverID);
        pstmt.executeUpdate();
        }
    }
}
