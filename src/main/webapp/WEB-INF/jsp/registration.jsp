<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Registration page</title>
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
<form id="registrationLayer" action="addUser" method="post">
    <h1><spring:message code="user.registration"/></h1>
    <p><spring:message code="user.fillForm"/></p>
    <hr>

    <table style="with: 50%">
        <tr>
            <spring:message code="user.placeholder.login" var="login"/>
            <td><label for="login"><b><spring:message code="user.login"/> </b></label></td>
            <td><input type="text" name="login" class="form-control input-lg"
                       placeholder="${login}" id="login" required></td>
        </tr>
        <tr>
            <spring:message code="user.placeholder.name" var="name"/>
            <td><label for="name"><b><spring:message code="user.name"/> </b></label></td>
            <td><input type="text" name="name" class="form-control input-lg"
                       placeholder="${name}" id="name" required></td>
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
    <button type="submit" class="btn btn-success"><spring:message code="user.register"/></button>
    <%--<button type="submit" class="registerbtn">Register</button>--%>
    <p>${submit}</p>
</form>
</body>
</html>
