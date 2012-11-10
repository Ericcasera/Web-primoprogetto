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
                <h4>Benvenuti a "vendo piante" , prego loggarsi</h4>
                <br>
            </div>
            <form action="Login" method="post" class="form-horizontal">
                  <div class="control-group">
                      <label class="control-label" for="username">Username</label>
                         <div class="controls">
                             <input type="text" id="username" name="username">
                         </div>
                  </div>
                  <div class="control-group">
                      <label class="control-label" for="password">Password</label>
                         <div class="controls">
                             <input type="password" id="password" name="password">
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
