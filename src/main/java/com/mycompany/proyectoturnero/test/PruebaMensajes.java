/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaMensajes {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/proyectoturnero";
    private static final String USER = "root";  // tu usuario
    private static final String PASSWORD = "";  // tu contraseña

    public static void main(String[] args) {
        try {
            // 1️⃣ Conectar a la base de datos
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");

            // 2️⃣ Mostrar todos los mensajes
            String selectSQL = "SELECT m.id, u.nombre, m.mensaje " +
                               "FROM mensajes m JOIN usuarios u ON m.usuario_id = u.id";
            PreparedStatement psSelect = conn.prepareStatement(selectSQL);
            ResultSet rs = psSelect.executeQuery();

            System.out.println("Mensajes actuales:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreUsuario = rs.getString("nombre");
                String mensaje = rs.getString("mensaje");
                System.out.println(id + " | " + nombreUsuario + " : " + mensaje);
            }

            // 3️⃣ Insertar un mensaje de prueba
            String insertSQL = "INSERT INTO mensajes (usuario_id, mensaje) VALUES (?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertSQL);
            psInsert.setInt(1, 1); // usuario_id = 1 (Ana)
            psInsert.setString(2, "Mensaje de prueba desde Java");
            int filas = psInsert.executeUpdate();
            System.out.println("Filas insertadas: " + filas);

            // Cerrar recursos
            rs.close();
            psSelect.close();
            psInsert.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar o manipular la base de datos.");
        }
    }
}

