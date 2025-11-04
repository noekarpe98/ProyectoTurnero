/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.dao;

import com.mycompany.proyectoturnero.modelo.Mensaje;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MensajeDAO {

    // Método para obtener todos los mensajes
    public List<Mensaje> obtenerMensajes() {
        List<Mensaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM mensajes";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Mensaje m = new Mensaje();
                m.setId(rs.getInt("id"));
                m.setUsuarioId(rs.getInt("usuario_id"));
                m.setTexto(rs.getString("texto"));
                m.setOrigen(rs.getString("origen"));
                Timestamp ts = rs.getTimestamp("fecha_hora");
                if (ts != null) {
                    m.setFechaHora(ts.toLocalDateTime());
                }
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener mensajes: " + e.getMessage());
        }
        return lista;
    }

    // Método para insertar un mensaje
    public boolean insertarMensaje(Mensaje m) {
        String sql = "INSERT INTO mensajes(usuario_id, texto, origen, fecha_hora) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getUsuarioId());
            ps.setString(2, m.getTexto());
            ps.setString(3, m.getOrigen());
            ps.setTimestamp(4, Timestamp.valueOf(m.getFechaHora()));
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar mensaje: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener mensajes por usuario
    public List<Mensaje> obtenerMensajesPorUsuario(int usuarioId) {
        List<Mensaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM mensajes WHERE usuario_id = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mensaje m = new Mensaje();
                    m.setId(rs.getInt("id"));
                    m.setUsuarioId(rs.getInt("usuario_id"));
                    m.setTexto(rs.getString("texto"));
                    m.setOrigen(rs.getString("origen"));
                    Timestamp ts = rs.getTimestamp("fecha_hora");
                    if (ts != null) {
                        m.setFechaHora(ts.toLocalDateTime());
                    }
                    lista.add(m);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener mensajes por usuario: " + e.getMessage());
        }
        return lista;
    }
}

