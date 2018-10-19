<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/crud.js"></script>
    <%--<script>--%>
    <%--$(function () {--%>
    <%--alert('Подключена последняя версия jQuery через Google хостинг');--%>
    <%--});--%>
    <%--</script>--%>
</head>
<body>

<form action="add" method="post">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <table style="with: 50%">
        <tr>
            <td><label for="login"><b>Login</b></label></td>
            <td><input type="text" name="login" placeholder="Please, enter your login" id="login" required></td>
        </tr>
        <tr>
            <td><label for="name"><b>Name</b></label></td>
            <td><input type="text" name="name" placeholder="Please, enter your name" id="name" required></td>
        </tr>
        <tr>
            <td><label for="password"><b>Password</b></label></td>
            <td><input type="password" name="password" placeholder="PLease, enter yout password" id="password" required>
            </td>
        </tr>
    </table>
    <hr>
    <button type="submit" class="registerbtn">Register</button>
    <p>${submit}</p>
</form>
</body>
</html>
