/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.dao;

import com.mycompany.proyectoturnero.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ✅ Listar todos los clientes
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido, dni, telefono, email, id_usuario FROM clientes ORDER BY nombre ASC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setDni(rs.getString("dni"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }


    public List<Cliente> buscarPorNombre(String filtro) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido FROM clientes " +
                     "WHERE LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ? ORDER BY nombre ASC LIMIT 10";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String patron = "%" + filtro.toLowerCase() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("id_cliente"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellido(rs.getString("apellido"));
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar clientes: " + e.getMessage());
        }

        return lista;
    }

    public Cliente obtenerPorId(int idCliente) {
        Cliente c = null;
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setDni(rs.getString("dni"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setIdUsuario(rs.getInt("id_usuario"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener cliente: " + e.getMessage());
        }

        return c;
    }

    public List<Cliente> buscarPorFiltro(String filtro) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, apellido, telefono FROM clientes " +
                     "WHERE LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ? OR telefono LIKE ? " +
                     "ORDER BY nombre ASC LIMIT 10";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String patron = "%" + filtro.toLowerCase() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, "%" + filtro + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("id_cliente"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellido(rs.getString("apellido"));
                    c.setTelefono(rs.getString("telefono"));
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al buscar clientes: " + e.getMessage());
        }

        return lista;
    }

    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellido, dni, email, telefono, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setInt(6, cliente.getIdUsuario());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, dni=?, email=?, telefono=? WHERE id_cliente=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDni());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setInt(6, cliente.getIdCliente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

}






