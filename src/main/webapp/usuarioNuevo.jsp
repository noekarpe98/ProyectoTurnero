<%-- 
    Document   : usuarioNuevo
    Created on : 30 oct 2025, 4:01:25 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    String rol = (String) session.getAttribute("usuarioRol");
    if (rol == null) rol = "";
    if (!"admin".equalsIgnoreCase(rol)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Usuario</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>

<body>
<jsp:include page="header.jsp" />

<main class="contenedor-turnosnuevos">
    <h2>Registrar Nuevo Usuario</h2>

    <form action="UsuarioServlet" method="post" class="formulario-nuevo">
        <input type="hidden" name="accion" value="nuevo">

        <div class="campo">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" required placeholder="Nombre completo">
        </div>

        <div class="campo">
            <label for="email">Correo electrónico:</label>
            <input type="email" id="email" name="email" required placeholder="usuario@ejemplo.com">
        </div>

        <div class="campo">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required placeholder="••••••••">
        </div>

        <div class="campo">
            <label for="idRol">Rol:</label>
            <select id="idRol" name="idRol" required>
                <option value="">Seleccionar...</option>
                <option value="1">Administrador</option>
                <option value="2">Empleado</option>
            </select>
        </div>
        <div class="form-buttons">    
        <button type="submit" class="guardar">Guardar</button>
        <a href="usuarios.jsp"><button type="button">Cancelar</button></a>
        </div>
    </form>
</main>

</body>
</html>

