<%-- 
    Document   : login
    Created on : 14 oct 2025, 5:42:09 p. m.
    Author     : Noe
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body class="login-body">
    <div class="login-container">
        <h2>Iniciar sesión</h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="login" method="post">
            <label>Email:</label><br>
            <input type="email" name="email" required><br>

            <label>Contraseña:</label><br>
            <input type="password" name="password" required><br>

            <input type="submit" value="Ingresar">
        </form>
    </div>
</body>
</html>


