package com.db;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class  DBComplaintsOperations {

    private Connection con;

    public DBComplaintsOperations(Connection con) {
        this.con = con;
    }

    // Get all menu items
    public List<Complaints> getComplaints() throws SQLException {
        List<Complaints> Complaints = new ArrayList<>(); // Corrected variable name to camelCase

        String query = "SELECT * FROM complaints"; // Ensure this is the correct table name
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int complaintID = rs.getInt("complaintID");
               int customerID = rs.getInt("customerID");
                String date = rs.getString("date");
                String description = rs.getString("description");

                Complaints complaints = new Complaints(complaintID, customerID, date, description);
                Complaints.add(complaints); // Correctly adding the menuItem object to the Complaints list

            }
        }
        System.out.println(Complaints); // Moved print statement outside the try block
        return Complaints;
    }

    // Insert a new menu item
    public void insertComplaints(int complaintID,  int customerID, String date, String description) throws SQLException {
        String query = "INSERT INTO complaints (complaintID, customerID, date, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, complaintID);
            pstmt.setInt(2, customerID);
            pstmt.setString(3, date);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
        }
    }

    // Update menu item information
    public void updateComplaints(int complaintID, int customerID, String date, String description)
            throws SQLException {
        String query = "UPDATE complaints SET customerID = ?, date = ?, description = ? WHERE complaintID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, customerID);
            pstmt.setString(2, date);
            pstmt.setString(3, description);
            pstmt.setInt(5, complaintID);
            pstmt.executeUpdate();
        }
    }

   // Delete a menu item by ID
   public void deleteComplaints(int complaintID) throws SQLException {
    String query = "DELETE FROM complaints WHERE complaintID = ?";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setInt(1, complaintID);
        pstmt.executeUpdate();
        }
    }
}
