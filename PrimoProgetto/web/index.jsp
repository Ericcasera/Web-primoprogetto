<%-- 
    Document   : index
    Created on : 5-nov-2012, 19.46.20
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="Bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="Bootstrap/css/grafica.css" rel="stylesheet">
        <title>Login</title>
    </head>
    <body>       
        <div class="login well">
            <div class="login-title" >
                <br>
                <img src="/PrimoProgetto/Images-site/logo_login.png">
                <br>
                <br>
                <br>
            </div>
            
            <form action="LoginController?op=login" method="post" class="form-horizontal">
                  <div class="control-group">
                      <label class="control-label" for="username">Username</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Username" type="text" id="username" name="username">
                         </div>
                  </div>
                  <div class="control-group">
                      <label class="control-label" for="password">Password</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Password" type="password" id="password" name="password">
                         </div>
                  </div>
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Login</button>
                             <button class="btn" type="reset" >Reset</button>
                         </div>
                  </div>   
                
            </form>
        </div>
    </body>
</html>
