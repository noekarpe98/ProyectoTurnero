<%-- 
    Document   : turnos
    Created on : 20 oct 2025, 7:48:14 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.proyectoturnero.dao.TurnoDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Turno" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    if(rol != null) rol = rol.trim();

    TurnoDAO turnoDAO = new TurnoDAO();
    List<Turno> turnos = turnoDAO.listarTurnos();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Turnos</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-turnos">
    <h2>Turnos Disponibles y Reservados</h2>

    <div class="filtros">
        <div class="filtro-estado">
            <label for="filtroEstado">Estado:</label>
            <select id="filtroEstado">
                <option value="">Todos</option>
                <option value="Disponible">Disponible</option>
                <option value="Reservado">Reservado</option>
            </select>
        </div>

        <div class="buscador-cliente"   >
            <label for="buscarCliente">Buscar cliente:</label>
            <input type="text" id="buscarCliente" placeholder="Nombre o teléfono">
        </div>

        <% if("admin".equalsIgnoreCase(rol)){ %>
        <div class="boton-nuevo">
            <a href="nuevoTurno.jsp"><button type="button" class="nuevo">Nuevo Turno</button></a>
        </div>
        <% } %>
    </div>


    <!-- 🧾 TABLA DE TURNOS -->
    <table id="tablaTurnos" class="tabla-turnos">
        <thead>
            <tr>
                <th>Fecha</th>
                <th>Hora</th>
                <th>Estado</th>
                <th>Cliente</th>
                <th>Teléfono</th>
                <% if("admin".equalsIgnoreCase(rol)){ %>
                    <th>Acciones</th>
                <% } %>
            </tr>
        </thead>
        <tbody>
            <% for(Turno t : turnos){ %>
            <tr>
                <td><%= t.getFecha() %></td>
                <td><%= t.getHora() %></td>
                <td class="estado <%= t.getEstado().toLowerCase() %>"><%= t.getEstado() %></td>
                <td class="cliente"><%= t.getClienteNombre() != null && !t.getClienteNombre().isEmpty() ? t.getClienteNombre() : "Sin asignar" %></td>
                <td class="telefono"><%= t.getClienteTelefono() != null && !t.getClienteTelefono().isEmpty() ? t.getClienteTelefono() : "-" %></td>

                <% if("admin".equalsIgnoreCase(rol)){ %>
                <td>
                    <form action="modificarTurno.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="idTurno" value="<%= t.getId() %>">
                        <button type="submit">Modificar</button>
                    </form>
                    <button type="button" class="eliminar" onclick="abrirModal(<%= t.getId() %>)">Eliminar</button>
                </td>
                <% } %>
            </tr>
            <% } %>
        </tbody>
    </table>
</main>

<!-- 🔴 MODAL DE CONFIRMACIÓN -->
<div id="modalConfirmacion" class="modal">
    <div class="modal-content">
        <h3>¿Estás seguro de eliminar este turno?</h3>
        <form id="formEliminar" action="TurnoServlet" method="post">
            <input type="hidden" name="accion" value="eliminar">
            <input type="hidden" id="idTurnoEliminar" name="idTurno">
            <div class="modal-buttons">
                <button type="button" class="btn-cancelar" onclick="cerrarModal()">Cancelar</button>
                <button type="submit" class="btn-eliminar">Eliminar</button>
            </div>
        </form>
    </div>
</div>

<script>
function abrirModal(idTurno) {
    document.getElementById('idTurnoEliminar').value = idTurno;
    document.getElementById('modalConfirmacion').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('modalConfirmacion').style.display = 'none';
}

window.onclick = function(event) {
    const modal = document.getElementById('modalConfirmacion');
    if (event.target === modal) cerrarModal();
}

// === FILTROS DE BÚSQUEDA ===
function filtrarTurnos() {
    const estadoFiltro = document.getElementById('filtroEstado').value.toLowerCase();
    const busqueda = document.getElementById('buscarCliente').value.toLowerCase();
    const filas = document.querySelectorAll('#tablaTurnos tbody tr');

    filas.forEach(fila => {
        const estado = fila.querySelector('.estado').textContent.toLowerCase();
        const cliente = fila.querySelector('.cliente').textContent.toLowerCase();
        const telefono = fila.querySelector('.telefono').textContent.toLowerCase();

        // Si el filtro de estado está vacío, mostramos todos
        const coincideEstado = estadoFiltro === '' || estado === estadoFiltro;

        const coincideBusqueda = cliente.includes(busqueda) || telefono.includes(busqueda);

        fila.style.display = (coincideEstado && coincideBusqueda) ? '' : 'none';
    });
}

// Escuchar cambios en los filtros
document.getElementById('filtroEstado').addEventListener('change', filtrarTurnos);
document.getElementById('buscarCliente').addEventListener('input', filtrarTurnos);

</script>

</body>
</html>






