/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.proyectoturnero.controlador;

import com.mycompany.proyectoturnero.dao.UsuarioDAO;
import com.mycompany.proyectoturnero.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarUsuarios(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> lista = usuarioDAO.obtenerUsuarios();
        request.setAttribute("listaUsuarios", lista);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("usuarioEditar.jsp").forward(request, response);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.eliminarUsuario(id);
        response.sendRedirect("UsuarioServlet?accion=listar");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("nuevo".equalsIgnoreCase(accion)) {
            insertarUsuario(request, response);
        } else if ("actualizar".equalsIgnoreCase(accion)) {
            actualizarUsuario(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void insertarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Usuario u = new Usuario();
        u.setNombre(request.getParameter("nombre"));
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        u.setIdRol(Integer.parseInt(request.getParameter("idRol")));

        boolean exito = usuarioDAO.insertarUsuario(u);

        if (exito) {
            response.sendRedirect("UsuarioServlet?accion=listar&mensaje=ok");
        } else {
            response.sendRedirect("UsuarioServlet?accion=listar&mensaje=error");
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Usuario u = new Usuario();
        u.setId(Integer.parseInt(request.getParameter("id")));
        u.setNombre(request.getParameter("nombre"));
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        u.setIdRol(Integer.parseInt(request.getParameter("idRol")));

        boolean exito = usuarioDAO.actualizarUsuario(u);

        if (exito) {
            response.sendRedirect("UsuarioServlet?accion=listar&mensaje=ok");
        } else {
            response.sendRedirect("UsuarioServlet?accion=listar&mensaje=error");
        }
    }
}
