package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {

    private Connection con;

    public DBOperations(Connection con) {
        this.con = con;
    }

    // get all customers
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM customers";
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int customerId = rs.getInt("cID");
                String cName = rs.getString("cName");
                String phone = rs.getString("phone");
                String content = rs.getString("content");
                int points = rs.getInt("points");

                Customer customer = new Customer(customerId, cName, phone, content, points);
                customers.add(customer);
            }
            System.out.println(customers.toString());
        }
        return customers;
    }

    // insert a new customer
    public void insertCustomer(int cID, String cName, String phone, String content, int points) throws SQLException {
        String query = "INSERT INTO customers (cID, cName, phone, content, points) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, cID);
        pstmt.setString(2, cName);
        pstmt.setString(3, phone);
        pstmt.setString(4, content);
        pstmt.setInt(5, points);
        pstmt.executeUpdate();
    }

    // update customer information
    public void updateCustomer(int customerId, String cName, String phone, String content, int points)
            throws SQLException {
        String query = "UPDATE customers SET cName = ?, phone = ?, content = ?, points = ? WHERE cID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, cName);
        pstmt.setString(2, phone);
        pstmt.setString(3, content);
        pstmt.setInt(4, points);
        pstmt.setInt(5, customerId);
        pstmt.executeUpdate();
    }

    // delete a customer by ID
    public void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM customers WHERE cID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, customerId);
        pstmt.executeUpdate();
    }
}
