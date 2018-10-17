<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>addUser</title>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="js/crud.js"></script>
    <%--<script>--%>
        <%--$(function () {--%>
            <%--alert('Подключена последняя версия jQuery через Google хостинг');--%>
        <%--});--%>
    <%--</script>--%>
<%--</head>--%>
<body>

<form action="add" method="post">
    <label for="name">Name</label>
    <input type="text" name="name" placeholder="Please, enter your name" id="name">
    <label for="login">login</label>
    <input type="text" name="login" placeholder="Please, enter your login" id="login">
    <label for="password">password</label>
    <input type="password" name="password" placeholder="Please, enter your password" id="password">
    <input type="submit" id="addValues" value="Add"></button>
    <p>${msg}</p>
    </form>
</body>
</html>
