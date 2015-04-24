<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style>
    #login-form {
        margin-top: 15%;
        text-align: center;
        margin-left: auto;
        margin-right: auto;
        font-size: x-large;
        font-weight: bold;
    }
</style>
<body>
<div id="login-form">
    <c:choose>
        <c:when test="${empty sessionScope.userId}">
            <h2>Login page</h2>

            <form action="/login" method="post">
                Username: <input type="text" name="username" value=""/>
                <br>
                <br>
                Password: <input type="password" name="password" value=""/>
                <br>
                <br>
                <button type="submit">Log in</button>
                <br>
            </form>
            <c:if test="${not empty requestScope.errorMessage}">
                <p style="color:red;">Error: ${requestScope.errorMessage}</p>
            </c:if>
        </c:when>
        <c:when test="${not empty sessionScope.userId}">
            Hello, ${sessionScope.userName}!
            <a href="/logout">Log out</a>
        </c:when>
    </c:choose>
</div>
</body>
</html>