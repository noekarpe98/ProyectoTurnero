<%-- 
    Document   : editarCliente
    Created on : 30 oct 2025, 12:19:28 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mycompany.proyectoturnero.dao.ClienteDAO" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Cliente" %>

<%
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    ClienteDAO dao = new ClienteDAO();
    Cliente cliente = dao.obtenerPorId(idCliente);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Cliente</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-clientes">
    <h2>Editar Cliente</h2>
    <form action="ClienteServlet" method="post" class="form-turno">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

        <label>Nombre:</label>
        <input type="text" name="nombre" value="<%= cliente.getNombre() %>" required>

        <label>Apellido:</label>
        <input type="text" name="apellido" value="<%= cliente.getApellido() %>" required>

        <label>DNI:</label>
        <input type="text" name="dni" value="<%= cliente.getDni() %>" required>

        <label>Email:</label>
        <input type="email" name="email" value="<%= cliente.getEmail() %>">

        <label>Teléfono:</label>
        <input type="text" name="telefono" value="<%= cliente.getTelefono() %>">

        <div class="botones-form">
            <button type="submit">Guardar Cambios</button>
            <a href="clientes.jsp"><button type="button" class="btn-cancelar">Cancelar</button></a>
        </div>
    </form>
</main>
</body>
</html>
