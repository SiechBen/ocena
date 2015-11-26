<%-- 
    Document   : lecturerpage
    Created on : Aug 13, 2015, 6:21:50 AM
    Author     : Ben Siech
--%>

<%@tag description="This is the parent tag for java server pages in the views folder" pageEncoding="UTF-8"%>

<%-- The list of normal attributes:--%>
<%@attribute name="title" required="true" %>

<%-- The list of fragments:--%>
<%@attribute name="content" fragment="true"%>

<%-- Recursive html content is specified below: --%>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="The tag file from which all jsps inherit from">
        <meta name="author" content="Ben Siech">
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">

        <title> ${title} </title>

        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

        <!-- Bootstrap core CSS -->
        <link href="static/plugins/bootstrap/bootstrap.min.css" rel="stylesheet" />

        <!-- Jquery ui CSS -->
        <link href="static/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />

        <link href="static/plugins/jquery-ui/jquery-ui.css" rel="stylesheet" />

        <!-- Custom styles for this template -->
        <link href="static/css/default.css" rel="stylesheet" />

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

        <script>
            $(function () {
                $("#accordion").accordion({header: "h1", collapsible: true, active: false});
            });
        </script>

    </head>

    <!-- The #page-top ID is part of the scrolling feature - the data-spy and data-target are part of the built-in Bootstrap scrollspy function -->

    <body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="http://www.uonbi.ac.ke/" id="uonbi-logo">
                                <img src="static/img/logo.png" alt="The University of Nairobi logo"/>
                                THE UNIVERSITY OF NAIROBI
                            </a>
                        </li>
                        <li class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/home')">
                                Home
                            </button>
                        </li>
                        <li class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow()">
                                Profile
                            </button>
                        </li>
                        <li class="dropdown">
                            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/logout')">
                                Logout
                            </button>
                        </li>
                    </ul>

                </div>

                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container -->

        </nav>

        <div class="container">
            <div id="content">
                <jsp:invoke fragment="content"/>
            </div>
            <div class="dialog" id="message-dialog">
                <p id="message"></p>
            </div>
        </div>

        <!-- Bootstrap core JavaScript
      ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="static/plugins/jquery/jquery.js"></script>
        <script src="static/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="static/plugins/i18n/jquery.i18n.properties.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="static/plugins/ie/ie10-viewport-bug-workaround.js"></script>
        <script src="static/plugins/jquery-ui/jquery-ui.js"></script>
        <script src="static/js/actions.js"></script>

    </body>

</html>
