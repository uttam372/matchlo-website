<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="./sighn.css"> 
</head>
<body>
    <div class="signuphead">
        <a href="index.jsp">HOME</a>
        <div class="rom">  
            <form action="SignupServlet" method="post"> <!-- Corrected action to "SignupServlet" -->
                <h2>Sign up here</h2>
                <p>Name: <input type="text" name="name" id="name"></p>
                <p>Email: <input type="email" name="email" id="email"></p>
                <p>State: <input type="text" name="state" id="state"></p>
                <p>District: <input type="text" name="district" id="district"></p>
                <p>Favorite game: <input type="text" name="game" id="game"></p>
                <p>Create password: <input type="password" name="password" id="password"></p>
                <p>Confirm password: <input type="password" name="pass" id="pass"></p>
                <button class="btn" type="submit">Sign Up</button>
            </form>
        </div>
    </div>
</body>
</html>
