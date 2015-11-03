<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ocena - online course/lecturer evaluation platform</title>
        <style type="text/css" media="screen">
            /* <![CDATA[ */
            @import url("static/plugins/bootstrap/bootstrap.min.css");
            @import url("static/plugins/jquery-ui/jquery-ui.min.css");
            @import url("static/plugins/jquery-ui/jquery-ui.css");
            @import url("static/css/default.css");
            /* ]]> */
        </style>
    </head>
    <body>
        <div id="index-wrapper">
            <h1>Login to proceed with course/lecturer evaluation</h1>
            <div>
                <form action="/Ocena/login" method="post" id="login-form">
                    <table class="table table-responsive table-hover">
                        <thead>
                            <tr>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="2">Use your registration number for password </td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <tr>
                                <td>Username: </td>
                                <td><input type="text" id="login-username" name="login-username" required="true"></td>
                            </tr>
                            <tr>
                                <td>Password: </td>
                                <td><input type="password" id="login-password" name="login-password" required="true"></td>
                            </tr>
                            <tr>
                                <td id="login-strip" colspan="2" class="default" onclick="loginUser()"> Login </td> 
                            </tr>
                        </tbody>
                    </table>
                </form>

                <div id="login-validity-info"></div>
                <div id="invalid-login-info"></div>

            </div>
        </div>

        <!-- Bootstrap core JavaScript
 ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="static/plugins/jquery/jquery.js"></script>
        <script src="static/plugins/bootstrap/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="static/plugins/ie/ie10-viewport-bug-workaround.js"></script>
        <script src="static/plugins/jquery-ui/jquery-ui.js"></script>
        <script src="static/js/actions.js"></script>

    </body>
</html>
