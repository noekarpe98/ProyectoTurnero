/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.dao;

import com.mycompany.proyectoturnero.modelo.Turno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    public boolean agregarTurno(Turno turno) {
        String sql = "INSERT INTO turnos (fecha, hora, estado, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, turno.getFecha());
            ps.setString(2, turno.getHora());
            ps.setString(3, turno.getEstado());

            if (turno.getIdCliente() != null) {
                ps.setInt(4, turno.getIdCliente());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Turno> listarTurnos() {
        List<Turno> lista = new ArrayList<>();

        String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, t.id_cliente, " +
                     "c.nombre AS cliente_nombre, c.telefono AS cliente_telefono " +
                     "FROM turnos t " +
                     "LEFT JOIN clientes c ON t.id_cliente = c.id_cliente";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turno t = new Turno();
                t.setId(rs.getInt("id_turno"));
                t.setFecha(rs.getString("fecha"));
                t.setHora(rs.getString("hora"));
                t.setEstado(rs.getString("estado"));
                t.setIdCliente(rs.getInt("id_cliente"));
                t.setClienteNombre(rs.getString("cliente_nombre"));
                t.setClienteTelefono(rs.getString("cliente_telefono"));
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean modificarTurno(Turno turno) {
        String sql = "UPDATE turnos SET fecha=?, hora=?, estado=?, id_cliente=? WHERE id_turno=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, turno.getFecha());
            ps.setString(2, turno.getHora());
            ps.setString(3, turno.getEstado());

            if (turno.getIdCliente() != null) {
                ps.setInt(4, turno.getIdCliente());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setInt(5, turno.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al modificar turno: " + e.getMessage());
            return false;
        }
    }


    public boolean eliminarTurno(int id) {
        String sql = "DELETE FROM turnos WHERE id_turno=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Turno obtenerPorId(int idTurno) {
    Turno t = null;
    String sql = "SELECT t.id_turno, t.fecha, t.hora, t.estado, t.id_cliente, " +
                 "c.nombre AS clienteNombre, c.apellido AS clienteApellido " +
                 "FROM turnos t LEFT JOIN clientes c ON t.id_cliente = c.id_cliente " +
                 "WHERE t.id_turno = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idTurno);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                t = new Turno();
                t.setId(rs.getInt("id_turno"));
                t.setFecha(rs.getString("fecha"));
                t.setHora(rs.getString("hora"));
                t.setEstado(rs.getString("estado"));
                t.setIdCliente(rs.getInt("id_cliente"));

                // Para mostrar el nombre completo del cliente
                String nombreCliente = rs.getString("clienteNombre");
                String apellidoCliente = rs.getString("clienteApellido");
                t.setClienteNombre(nombreCliente != null ? nombreCliente + " " + apellidoCliente : "");
            }
        }

    } catch (SQLException e) {
        System.err.println("❌ Error al obtener turno: " + e.getMessage());
    }

    return t;
}

}







