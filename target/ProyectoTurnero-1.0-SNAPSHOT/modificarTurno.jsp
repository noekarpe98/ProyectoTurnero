<%-- 
    Document   : modificarTurno
    Created on : 28 oct 2025, 2:52:21 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mycompany.proyectoturnero.dao.TurnoDAO" %>
<%@ page import="com.mycompany.proyectoturnero.dao.ClienteDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Turno" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Cliente" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    if(rol == null || !"admin".equalsIgnoreCase(rol.trim())){
        response.sendRedirect("turnos.jsp");
        return;
    }

    String idTurnoStr = request.getParameter("idTurno");
    if(idTurnoStr == null){
        response.sendRedirect("turnos.jsp");
        return;
    }

    int idTurno = Integer.parseInt(idTurnoStr);
    TurnoDAO turnoDAO = new TurnoDAO();
    Turno turno = turnoDAO.obtenerPorId(idTurno);

    ClienteDAO clienteDAO = new ClienteDAO();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Turno</title>
    <link rel="stylesheet" href="css/estilos.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-turnos">
    <h2>Modificar Turno #<%= turno.getId() %></h2>

    <form action="<%= request.getContextPath() %>/TurnoServlet" method="post" id="formTurno">
        <input type="hidden" name="accion" value="modificar">
        <input type="hidden" name="idTurno" value="<%= turno.getId() %>">

        <!-- 1. Estado -->
        <label>Tipo de Turno:</label>
        <select name="estado" id="estadoTurno" class="select-estilo" required>
            <option value="Disponible" <%= "Disponible".equalsIgnoreCase(turno.getEstado()) ? "selected" : "" %>>Disponible</option>
            <option value="Reservado" <%= "Reservado".equalsIgnoreCase(turno.getEstado()) ? "selected" : "" %>>Reservado</option>
        </select><br><br>

        <!-- 2. Cliente -->
        <div id="clienteDiv">
            <label>Cliente:</label>
            <input type="text" id="clienteInput" class="buscador-estilo" placeholder="Nombre, apellido o teléfono" autocomplete="off" 
                   value="<%= turno.getClienteNombre() != null ? turno.getClienteNombre() : "" %>">
            <input type="hidden" name="idCliente" id="idCliente" value="<%= turno.getIdCliente() %>">
            <div id="sugerencias"></div>
            <button type="button" id="btnAgregarCliente">Agregar Cliente Nuevo</button>
        </div><br>

        <!-- 3. Fecha -->
        <label>Fecha:</label>
        <input type="date" name="fecha" required value="<%= turno.getFecha() %>"><br><br>

        <!-- 4. Hora -->
        <label>Hora:</label>
        <input type="time" name="hora" required value="<%= turno.getHora() %>"><br><br>

        <button type="submit" class="guardar">Guardar cambios</button>
        <a href="turnos.jsp"><button type="button">Cancelar</button></a>
    </form>
</main>

<script>
$(document).ready(function(){

    // Mostrar u ocultar cliente según estado
    $('#estadoTurno').change(function(){
        if($(this).val() === 'Reservado'){
            $('#clienteDiv').show();
        } else {
            $('#clienteDiv').hide();
            $('#idCliente').val('');
            $('#clienteInput').val('');
            $('#sugerencias').empty().hide();
        }
    }).trigger('change');

    // Autocompletado de cliente
    $('#clienteInput').on('input', function(){
        let texto = $(this).val().trim();
        if(texto.length < 1){
            $('#sugerencias').empty().hide();
            $('#btnAgregarCliente').hide();
            $('#idCliente').val('');
            return;
        }

        $.ajax({
            url: '<%= request.getContextPath() %>/ClienteServlet',
            method: 'GET',
            data: { accion: 'buscar', filtro: texto },
            success: function(data){
                if(data.length > 0){
                    let html = '';
                    data.forEach(function(c){
                        html += '<div class="opcionCliente" data-id="'+c.idCliente+'">'+c.nombre+' '+c.apellido+' - '+c.telefono+'</div>';
                    });
                    $('#sugerencias').html(html).show();
                    $('#btnAgregarCliente').hide();
                } else {
                    $('#sugerencias').empty().hide();
                    $('#btnAgregarCliente').show();
                }
            },
            error: function(xhr, status, error){
                console.error("Error AJAX:", status, error);
            }
        });
    });

    // Selección de cliente
    $(document).on('click', '.opcionCliente', function(){
        $('#clienteInput').val($(this).text());
        $('#idCliente').val($(this).data('id'));
        $('#sugerencias').empty().hide();
        $('#btnAgregarCliente').hide();
    });

    // Redirigir a formulario de cliente nuevo
    $('#btnAgregarCliente').click(function(){
        window.location.href = '<%= request.getContextPath() %>/clienteNuevo.jsp';
    });

    // Cerrar sugerencias al hacer click fuera
    $(document).click(function(e){
        if(!$(e.target).closest('#clienteDiv').length){
            $('#sugerencias').empty().hide();
        }
    });

});
</script>

</body>
</html>

