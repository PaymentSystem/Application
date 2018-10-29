<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Account</title>
</head>
<body>
<a href="?lang=en">EN</a> | <a href="?lang=ru">RU</a>
<form id="addAcc" action="addAccount" method="post">
    <h1>Create account</h1>
    <p><spring:message code="user.fillForm"/></p>
    <hr>

    <table style="with: 50%">
        <td><label for="login"><b>Amount</b></label></td>
        <td><input name="amount" class="form-control input-lg"
                   id="login" required></td>

    </table>
    <button type="submit" class="btn btn-success"><spring:message code="user.register"/></button>
    <p>${submit}</p>
</form>
</body>
</html>
