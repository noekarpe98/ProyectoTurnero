<%-- 
    Document   : header
    Created on : 20 oct 2025, 7:46:57 p. m.
    Author     : Noe
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<header>
    <div class="header-container">
        <h1>Turnero - Gestión de Turnos</h1>
        <nav class="header-nav">
            <div class="links">
                <a href="turnos.jsp">Turnos</a>
                <a href="clientes.jsp">Clientes</a>
                <a href="usuarios.jsp">Usuarios</a>
            </div>
            <%-- Mostrar cerrar sesión solo si hay sesión activa --%>
            <%
                String usuario = (String) session.getAttribute("usuarioNombre");
                if (usuario != null) {
            %>
                <a href="LogoutServlet" class="header-containerdos">Cerrar sesión</a>
            <%
                }
            %>
        </nav>
    </div>
</header>




