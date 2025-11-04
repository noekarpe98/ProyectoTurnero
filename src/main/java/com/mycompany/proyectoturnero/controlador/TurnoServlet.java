/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoturnero.controlador;

import com.mycompany.proyectoturnero.dao.TurnoDAO;
import com.mycompany.proyectoturnero.modelo.Turno;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TurnoServlet")
public class TurnoServlet extends HttpServlet {

    private final TurnoDAO turnoDAO = new TurnoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rol = (String) request.getSession().getAttribute("usuarioRol");
        if (rol == null || !"admin".equalsIgnoreCase(rol.trim())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permisos para esta acción");
            return;
        }

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nuevo":
                registrarNuevoTurno(request, response);
                break;
            case "modificar":
                modificarTurno(request, response);
                break;
            case "eliminar":
                eliminarTurno(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida");
        }
    }

    // ----------------- NUEVO TURNO -----------------
    private void registrarNuevoTurno(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            String fecha = request.getParameter("fecha");
            String hora = request.getParameter("hora");
            String estado = request.getParameter("estado");
            String idClienteParam = request.getParameter("idCliente");

            Turno turno = new Turno();
            turno.setFecha(fecha);
            turno.setHora(hora);
            turno.setEstado(estado);

            if ("Reservado".equalsIgnoreCase(estado) && idClienteParam != null && !idClienteParam.isEmpty()) {
                turno.setIdCliente(Integer.parseInt(idClienteParam));
            } else {
                turno.setIdCliente(null); // Cliente NULL para turnos disponibles
            }

            boolean exito = turnoDAO.agregarTurno(turno);

            if (exito) {
                response.sendRedirect("turnos.jsp?msg=exito");
            } else {
                request.setAttribute("error", "No se pudo registrar el turno");
                request.getRequestDispatcher("nuevoTurno.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar el turno: " + e.getMessage());
            request.getRequestDispatcher("nuevoTurno.jsp").forward(request, response);
        }
    }

    // ----------------- MODIFICAR TURNO -----------------
    private void modificarTurno(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        try {
            int idTurno = Integer.parseInt(request.getParameter("idTurno"));
            String fecha = request.getParameter("fecha");
            String hora = request.getParameter("hora");
            String estado = request.getParameter("estado");
            String idClienteParam = request.getParameter("idCliente");

            Turno turno = new Turno();
            turno.setId(idTurno);
            turno.setFecha(fecha);
            turno.setHora(hora);
            turno.setEstado(estado);

            if ("Reservado".equalsIgnoreCase(estado) && idClienteParam != null && !idClienteParam.isEmpty()) {
                turno.setIdCliente(Integer.parseInt(idClienteParam));
            } else {
                turno.setIdCliente(null); // Cliente NULL para turnos disponibles
            }

            boolean exito = turnoDAO.modificarTurno(turno);

            if (exito) {
                response.sendRedirect("turnos.jsp?msg=modificado");
            } else {
                request.setAttribute("error", "No se pudo modificar el turno");
                request.getRequestDispatcher("modificarTurno.jsp?idTurno=" + idTurno).forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al modificar el turno: " + e.getMessage());
            request.getRequestDispatcher("modificarTurno.jsp?idTurno=" + request.getParameter("idTurno")).forward(request, response);
        }
    }

    // ----------------- ELIMINAR TURNO -----------------
    private void eliminarTurno(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            int id = Integer.parseInt(request.getParameter("idTurno"));
            boolean exito = turnoDAO.eliminarTurno(id);
            response.sendRedirect(exito ? "turnos.jsp?msg=eliminado" : "turnos.jsp?error=errorEliminar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("turnos.jsp?error=errorEliminar");
        }
    }
}








    


