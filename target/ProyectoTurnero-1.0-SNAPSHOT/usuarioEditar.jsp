<%-- 
    Document   : editarUsuario
    Created on : 30 oct 2025, 1:06:24 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mycompany.proyectoturnero.modelo.Usuario" %>

<%
    String rolSesion = (String) session.getAttribute("usuarioRol");
    if (rolSesion == null) rolSesion = "";

    Usuario usuario = (Usuario) request.getAttribute("usuario");
    if (usuario == null) {
%>
    <p style="color:red; text-align:center;">Usuario no encontrado.</p>
    <a href="UsuarioServlet?accion=listar">Volver</a>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="contenedor-clientes">
    <h2>Editar Usuario</h2>

    <form action="UsuarioServlet" method="post" class="formulario">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="id" value="<%= usuario.getId() %>">

        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= usuario.getNombre() %>" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= usuario.getEmail() %>" required>
        </div>

        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="text" id="password" name="password" value="<%= usuario.getPassword() %>" required>
        </div>

        <div class="form-group">
            <label for="idRol">Rol:</label>
            <select name="idRol" id="idRol" required>
                <option value="1" <%= usuario.getIdRol() == 1 ? "selected" : "" %>>Administrador</option>
                <option value="2" <%= usuario.getIdRol() == 2 ? "selected" : "" %>>Empleado</option>
                <option value="3" <%= usuario.getIdRol() == 3 ? "selected" : "" %>>Cliente</option>
            </select>
        </div>

        <div class="form-buttons">
            <button type="submit" class="btn-guardar">Guardar</button>
            <a href="UsuarioServlet?accion=listar" class="btn-cancelar">Cancelar</a>
        </div>
    </form>
</main>

</body>
</html>

