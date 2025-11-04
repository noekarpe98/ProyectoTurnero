/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/proyectoturnero?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";   
    private static final String PASS = "";  

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión establecida correctamente");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error en la conexión: " + e.getMessage());
        }
        return con;
    }
}

