/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.dao;

import com.mycompany.proyectoturnero.modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, r.nombre_rol " +
                     "FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("contraseña"));
                u.setRol(rs.getString("nombre_rol"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }

        return lista;
    }

    // Buscar usuario por ID
    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, r.nombre_rol " +
                     "FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.id_usuario = ?";
        Usuario u = null;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("contraseña"));
                    u.setRol(rs.getString("nombre_rol"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }

        return u;
    }

    // Insertar un nuevo usuario
    public boolean insertarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, email, contraseña, id_rol) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getIdRol());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // Actualizar un usuario existente
    public boolean actualizarUsuario(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, contraseña = ?, id_rol = ? WHERE id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getIdRol());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Validar login por email y contraseña
    public Usuario validarLogin(String email, String password) {
        Usuario usuario = null;
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, r.nombre_rol " +
                     "FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.email = ? AND u.contraseña = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("contraseña"));
                    usuario.setRol(rs.getString("nombre_rol"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al validar login: " + e.getMessage());
        }

        return usuario;
    }
        // Eliminar usuario por ID
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

}

