<%-- 
    Document   : nuevoTurno
    Created on : 21 oct 2025, 6:32:29 p. m.
    Author     : Noe
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mycompany.proyectoturnero.dao.ClienteDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Cliente" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    if(rol == null || !"admin".equalsIgnoreCase(rol.trim())){
        response.sendRedirect("turnos.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Turno</title>
    <link rel="stylesheet" href="css/estilos.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-turnosnuevos">
    <h2>Registrar Nuevo Turno</h2>

    <form action="<%= request.getContextPath() %>/TurnoServlet" method="post" id="formTurno">
        <input type="hidden" name="accion" value="nuevo">

        <!-- 1. Tipo de Turno / Estado -->
        <label>Tipo de Turno:</label>
        <select name="estado" id="estadoTurno" class="select-estilo" required>
            <option value="Disponible" selected>Disponible</option>
            <option value="Reservado">Reservado</option>
        </select><br><br>

        <!-- 2. Cliente (solo si Reservado) -->
        <div id="clienteDiv">
            <label>Cliente:</label>
            <input type="text" id="clienteInput" class="buscador-estilo" placeholder="Nombre, apellido o teléfono" autocomplete="off">
            <input type="hidden" name="idCliente" id="idCliente">
            <div id="sugerencias"></div>
            <button type="button" id="btnAgregarCliente">Agregar Cliente Nuevo</button>
        </div><br>

        <!-- 3. Fecha -->
        <label>Fecha:</label>
        <input type="date" name="fecha" required><br><br>

        <!-- 4. Hora -->
        <label>Hora:</label>
        <input type="time" name="hora" required><br><br>

        <button type="submit" class="guardar">Guardar</button>
        <a href="turnos.jsp"><button type="button">Cancelar</button></a>
    </form>

</main>

<script>
$(document).ready(function(){

    // Inicialmente ocultamos el div de cliente
    $('#clienteDiv').hide();

    $('#estadoTurno').change(function(){
        if($(this).val() === 'Reservado'){
            $('#clienteDiv').show();
            $('#idCliente').val('');
            $('#clienteInput').val('');
            $('#sugerencias').empty().hide();
            $('#btnAgregarCliente').hide();
        } else {
            $('#clienteDiv').hide();
            $('#idCliente').val(''); // <- importante para que sea NULL en el servlet
            $('#clienteInput').val('');
            $('#sugerencias').empty().hide();
            $('#btnAgregarCliente').hide();
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




