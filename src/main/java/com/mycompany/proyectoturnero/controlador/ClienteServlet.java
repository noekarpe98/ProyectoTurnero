/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.proyectoturnero.controlador;

import com.google.gson.Gson;
import com.mycompany.proyectoturnero.dao.ClienteDAO;
import com.mycompany.proyectoturnero.modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("buscar".equalsIgnoreCase(accion)) {
            buscarClientes(request, response);
        } else if ("listar".equalsIgnoreCase(accion)) {
            listarClientes(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida");
        }
    }

    private void buscarClientes(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json; charset=UTF-8");

        String filtro = request.getParameter("filtro");
        if (filtro == null) filtro = "";

        List<Cliente> clientes = clienteDAO.buscarPorFiltro(filtro);

        String json = new Gson().toJson(clientes);

        try (PrintWriter out = response.getWriter()) {
            out.print(json);
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Cliente> clientes = clienteDAO.listarClientes();
        request.setAttribute("listaClientes", clientes);
        request.getRequestDispatcher("clientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("nuevo".equalsIgnoreCase(accion)) {
            agregarCliente(request, response);
        } else if ("actualizar".equalsIgnoreCase(accion)) { // ✅ NUEVA ACCIÓN
            actualizarCliente(request, response);
        } else if ("eliminar".equalsIgnoreCase(accion)) {
            eliminarCliente(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida");
        }
    }

    private void agregarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        HttpSession sesion = request.getSession(false);
        int idUsuario = 0;
        if (sesion != null && sesion.getAttribute("usuarioId") != null) {
            idUsuario = (int) sesion.getAttribute("usuarioId");
        }

        Cliente nuevo = new Cliente();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setDni(dni);
        nuevo.setEmail(email);
        nuevo.setTelefono(telefono);
        nuevo.setIdUsuario(idUsuario);

        boolean exito = clienteDAO.agregarCliente(nuevo);

        if (exito) {
            response.sendRedirect("clientes.jsp?mensaje=ok");
        } else {
            response.sendRedirect("clientes.jsp?mensaje=error");
        }
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("idCliente");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("clientes.jsp?mensaje=error_id");
            return;
        }

        int idCliente;
        try {
            idCliente = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("clientes.jsp?mensaje=error_id");
            return;
        }

        boolean exito = clienteDAO.eliminarCliente(idCliente);

        if (exito) {
            response.sendRedirect("clientes.jsp?mensaje=eliminado");
        } else {
            response.sendRedirect("clientes.jsp?mensaje=error_eliminar");
        }
    }

    // ✅ NUEVO MÉTODO PARA ACTUALIZAR CLIENTES
    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);

        boolean exito = clienteDAO.actualizarCliente(cliente);

        if (exito) {
            response.sendRedirect("clientes.jsp?mensaje=actualizado");
        } else {
            response.sendRedirect("editarCliente.jsp?idCliente=" + idCliente + "&error=1");
        }
    }
}




