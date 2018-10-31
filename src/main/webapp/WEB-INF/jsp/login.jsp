<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Login page</title>
    <style type="text/css">
        #registrationLayer {
            margin: 0 25% 0 25%;
            width: 50%; /* Ширина слоя */
            background: #c3c3c3; /* Цвет фона */
            padding: 10px; /* Поля вокруг текста */
        }
    </style>

</head>

<body>
<a href="?lang=en">EN</a> | <a href="?lang=ru">RU</a>
<form id="loginLayer" action="login" method="post">
    <h1><spring:message code="user.login"/></h1>
    <p><spring:message code="user.fillLogin"/></p>
    <hr>

    <table style="with: 50%">
        <tr>
            <spring:message code="user.placeholder.login" var="login"/>
            <td><label for="login"><b><spring:message code="user.login"/> </b></label></td>
            <td><input type="text" name="username" class="form-control input-lg"
                       placeholder="${login}" id="login" required></td>
        </tr>
        <tr>
            <spring:message code="user.placeholder.password" var="password"/>
            <td><label for="password"><b><spring:message code="user.password"/> </b></label></td>
            <td><input type="password" name="password" class="form-control input-lg"
                       placeholder="${password}" id="password" required>
            </td>
        </tr>
    </table>
    <hr>
    <button type="submit" class="btn btn-success">Login</button>
        <%--<button type="submit" class="registerbtn">Login</button>--%>
    <p>${submit}</p>
</form>
</body>
</html>
