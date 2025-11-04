<%-- 
    Document   : clientes
    Created on : 28 oct 2025, 5:42:08 p. m.
    Author     : Noe
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.proyectoturnero.dao.ClienteDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Cliente" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    if (rol == null) rol = "";

    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> clientes = clienteDAO.listarClientes();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Clientes</title>
    <link rel="stylesheet" href="css/estilos.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-clientes">
    <h2>Listado de Clientes</h2>
    <div class="filtros">
        <label for="buscarCliente">Buscar:</label>
        <input type="text" id="buscarCliente" placeholder="Nombre, Apellido o DNI">
        <% if ("admin".equalsIgnoreCase(rol)) { %>
            <a href="clienteNuevo.jsp"><button type="button" class="nuevocliente">Nuevo Cliente</button></a>
        <% } %>
    </div>

    <table id="tablaClientes" class="tabla-turnos">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>DNI</th>
                <th>Email</th>
                <th>Teléfono</th>
                <% if ("admin".equalsIgnoreCase(rol)) { %>
                    <th>Acciones</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <% if (clientes != null && !clientes.isEmpty()) { %>
                <% for (Cliente c : clientes) { %>
                    <tr>
                        <td><%= c.getNombre() %></td>
                        <td><%= c.getApellido() %></td>
                        <td><%= c.getDni() %></td>
                        <td><%= c.getEmail() %></td>
                        <td><%= c.getTelefono() != null && !c.getTelefono().isEmpty() ? c.getTelefono() : "-" %></td>

                        <% if ("admin".equalsIgnoreCase(rol)) { %>
                        <td>
                            <!-- 🖊 BOTÓN EDITAR -->
                            <form action="editarCliente.jsp" method="get" style="display:inline;">
                                <input type="hidden" name="idCliente" value="<%= c.getIdCliente() %>">
                                <button type="submit">Editar</button>
                            </form>

                            <!-- 🗑 BOTÓN ELIMINAR -->
                            <button type="button" class="eliminar" onclick="abrirModalEliminar(<%= c.getIdCliente() %>)">Eliminar</button>
                        </td>
                        <% } %>
                    </tr>
                <% } %>
            <% } else { %>
                <tr><td colspan="6" style="text-align:center;">No hay clientes cargados.</td></tr>
            <% } %>
        </tbody>
    </table>
</main>

<!-- 🗑 MODAL ELIMINAR CLIENTE -->
<div id="modalEliminar" class="modal" style="display:none;">
    <div class="modal-content">
        <h3>¿Deseas eliminar este cliente?</h3>
        <form action="ClienteServlet" method="post">
            <input type="hidden" id="idClienteEliminar" name="idCliente">
            <input type="hidden" name="accion" value="eliminar">
            <div class="modal-buttons">
                <button type="button" class="btn-cancelar" onclick="cerrarModalEliminar()">Cancelar</button>
                <button type="submit" class="btn-eliminar">Eliminar</button>
            </div>
        </form>

    </div>
</div>

<script>
function abrirModalEliminar(id) {
    document.getElementById('idClienteEliminar').value = id;
    document.getElementById('modalEliminar').style.display = 'flex';
}
function cerrarModalEliminar() {
    document.getElementById('modalEliminar').style.display = 'none';
}
window.onclick = function(event) {
    const modal = document.getElementById('modalEliminar');
    if (event.target === modal) cerrarModalEliminar();
}

// 🔍 BUSCADOR DINÁMICO
$(document).ready(function() {
    $("#buscarCliente").on("keyup", function() {
        const valor = $(this).val().toLowerCase();
        $("#tablaClientes tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(valor) > -1);
        });
    });
});
</script>

</body>
</html>





