<%-- 
    Document   : usuarios.jsp
    Created on : 30 oct 2025, 12:57:30 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.proyectoturnero.dao.UsuarioDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Usuario" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    if (rol == null) rol = "";

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>
    <link rel="stylesheet" href="css/estilos.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-clientes">
    <h2>Listado de Usuarios</h2>
    <div class="filtros">
        <label for="buscarUsuario">Buscar:</label>
        <input type="text" id="buscarUsuario" placeholder="Nombre o Email">
        <% if ("admin".equalsIgnoreCase(rol)) { %>
            <a href="usuarioNuevo.jsp"><button type="button" class="nuevocliente">Nuevo Usuario</button></a>
        <% } %>
    </div>

    <table id="tablaUsuarios" class="tabla-turnos">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>Contraseña</th>
                <th>Rol</th>
                <% if ("admin".equalsIgnoreCase(rol)) { %>
                    <th>Acciones</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <% if (usuarios != null && !usuarios.isEmpty()) { %>
                <% for (Usuario u : usuarios) { %>
                    <tr>
                        <td><%= u.getNombre() %></td>
                        <td><%= u.getEmail() %></td>
                        <td><%= u.getPassword() %></td>
                        <td><%= u.getRol() %></td>

                        <% if ("admin".equalsIgnoreCase(rol)) { %>
                        <td>
                            <!-- 🖊 BOTÓN EDITAR -->
                            <form action="UsuarioServlet" method="get" style="display:inline;">
                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="id" value="<%= u.getId() %>">
                                <button type="submit">Editar</button>
                            </form>

                            <!-- 🗑 BOTÓN ELIMINAR -->
                            <button type="button" class="eliminar" onclick="abrirModalEliminar(<%= u.getId() %>)">Eliminar</button>
                        </td>

                        <% } %>
                    </tr>
                <% } %>
            <% } else { %>
                <tr><td colspan="5" style="text-align:center;">No hay usuarios cargados.</td></tr>
            <% } %>
        </tbody>
    </table>
</main>

<!-- 🗑 MODAL ELIMINAR USUARIO -->
<div id="modalEliminar" class="modal" style="display:none;">
    <div class="modal-content">
        <h3>¿Deseas eliminar este usuario?</h3>
        <form action="UsuarioServlet" method="get">
            <input type="hidden" id="idUsuarioEliminar" name="id">
            <input type="hidden" name="accion" value="eliminar">
            <div class="modal-buttons">
                <button type="button" class="btn-cancelar" onclick="cerrarModalEliminar()">Cancelar</button>
                <button type="submit" class="btn-eliminar">Eliminar</button>
            </div>
        </form>

    </div>
</div>

<script>
// 🗑 MODAL ELIMINAR
function abrirModalEliminar(id) {
    document.getElementById('idUsuarioEliminar').value = id;
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
    $("#buscarUsuario").on("keyup", function() {
        const valor = $(this).val().toLowerCase();
        $("#tablaUsuarios tbody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(valor) > -1);
        });
    });
});
</script>

</body>
</html>

