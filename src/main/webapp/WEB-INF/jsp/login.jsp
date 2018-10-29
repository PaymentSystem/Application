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

<form id="loginLayer" action="login" method="post">
    <h1>Login</h1>
    <p>Please fill in this form to login.</p>
    <hr>

    <table style="with: 50%">
        <tr>
            <td><label for="login"><b>Login</b></label></td>
            <td><input type="text" name="username" class="form-control input-lg"
                       placeholder="Please, enter your login" id="login" required></td>
        </tr>
        <tr>
            <td><label for="password"><b>Password</b></label></td>
            <td><input type="password" name="password" class="form-control input-lg"
                       placeholder="Please, enter your password" id="password" required>
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
