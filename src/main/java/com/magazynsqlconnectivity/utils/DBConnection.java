/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magazynsqlconnectivity.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author xxbar
 */
public class DBConnection {

    static final String DB_URL = "jdbc:mysql://localhost/magazynpsqlconnectivity?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    static final String USER = "root";
    static final String PASS = "password";
    
    private static DBConnection instance;

    public DBConnection() {
        
    }
    
    public synchronized static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        return conn;
    }
}
