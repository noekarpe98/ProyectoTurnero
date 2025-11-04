/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.controlador;

import com.mycompany.proyectoturnero.dao.UsuarioDAO;
import com.mycompany.proyectoturnero.modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validar usuario con el DAO
        Usuario usuario = usuarioDAO.validarLogin(email, password);

        if (usuario != null) {
            // Login exitoso: guardar datos en sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioNombre", usuario.getNombre());
            session.setAttribute("usuarioRol", usuario.getRol());

            // Redirigir a la página principal de turnos
            response.sendRedirect("turnos.jsp");
        } else {
            // Login fallido: enviar mensaje de error al login.jsp
            request.setAttribute("error", "Email o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si alguien accede vía GET, redirige al login
        response.sendRedirect("login.jsp");
    }
}

