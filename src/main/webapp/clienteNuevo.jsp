<%-- 
    Document   : clienteNuevo
    Created on : 29 oct 2025, 10:41:34 a. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Usuario" %>

<%
    String rol = (String) session.getAttribute("usuarioRol");
    Integer idUsuario = (Integer) session.getAttribute("usuarioId");

    if(rol == null || idUsuario == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Cliente</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-clientes">
    <h2>Registrar Nuevo Cliente</h2>

    <form action="<%= request.getContextPath() %>/ClienteServlet" method="post" id="formCliente">
        <input type="hidden" name="accion" value="nuevo">
        <input type="hidden" name="idUsuario" value="<%= idUsuario %>">

        <!-- 🧍 Nombre -->
        <label>Nombre:</label>
        <input type="text" name="nombre" required><br><br>

        <!-- 🧍 Apellido -->
        <label>Apellido:</label>
        <input type="text" name="apellido" required><br><br>

        <!-- 🪪 DNI -->
        <label>DNI:</label>
        <input type="text" name="dni" required><br><br>

        <!-- 📧 Email -->
        <label>Email:</label>
        <input type="email" name="email" required><br><br>

        <!-- ☎️ Teléfono -->
        <label>Teléfono:</label>
        <input type="text" name="telefono" required><br><br>

        <!-- 🔘 Botones -->
        <button type="submit" class="guardar">Guardar</button>
        <a href="clientes.jsp"><button type="button">Cancelar</button></a>
    </form>
</main>

</body>
</html>

