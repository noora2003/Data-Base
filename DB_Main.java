
package com.db;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// import javax.management.relation.Role;

public class DB_Main extends Application {

    // arraylists for employee, login, users, roles, orders, customers, menuitems
    @FXML
    public static ArrayList<EmployeeData> employees;
    public static ArrayList<Login> loginList;
    public static ArrayList<Users> users;
    public static ArrayList<Roles> roles;
    // public static ArrayList<Order> orders;
    public static ArrayList<Customer> customers;
    public static ArrayList<Menus> menus;
    public static ArrayList<Items> items;

    private static Scene scene;
    private static Connection databaseConnection;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Connection getDatabaseConnection() {
        return databaseConnection;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DB_Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();
        getRoles();
        getUsers();
        getLogin();
        getMenus();
        getItems();
        launch();
        disconnect();
    }

    private static void connect() throws ClassNotFoundException, SQLException {
        Connector.con.connectDB();
        System.out.println("Connection established");

    }

    private static void disconnect() throws ClassNotFoundException, SQLException {
        Connector.con.connectDB().close();
        System.out.println("Connection closed");
    }

    private static void getUsers() throws SQLException, ClassNotFoundException {

        String SQL;
        users = new ArrayList<>();

        SQL = "select * from Users";
        Statement stmt = Connector.con.connectDB().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            users.add(new Users(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4)));
        }
        rs.close();
        stmt.close();
    }

    private static void getLogin() throws SQLException, ClassNotFoundException {

        String SQL;
        loginList = new ArrayList<>();

        SQL = "select * from Login";
        Statement stmt = Connector.con.connectDB().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            loginList.add(new Login(rs.getInt(1), rs.getDate(2),
                    rs.getInt(3)));
        }
        rs.close();
        stmt.close();

    }

    private static void getRoles() throws SQLException, ClassNotFoundException {

        String SQL;
        roles = new ArrayList<>();

        SQL = "select * from Roles";
        Statement stmt = Connector.con.connectDB().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            roles.add(new Roles(rs.getInt(1), rs.getString(2)));
        }
        rs.close();
        stmt.close();
    }

    private static void getMenus() throws SQLException, ClassNotFoundException {

        String SQL;
        menus = new ArrayList<>();

        SQL = "select * from menus";
        Statement stmt = Connector.con.connectDB().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            menus.add(new Menus(rs.getInt(1), rs.getString(2)));
        }
        rs.close();
        stmt.close();
    }

    private static void getItems() throws SQLException, ClassNotFoundException {

        String SQL;
        items = new ArrayList<>();

        SQL = "select * from items";
        Statement stmt = Connector.con.connectDB().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            items.add(new Items(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
        }
        rs.close();
        stmt.close();
    }
}