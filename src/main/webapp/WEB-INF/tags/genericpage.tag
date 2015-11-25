<%-- 
    Document   : genericpage
    Created on : Nov 25, 2015, 8:48:34 PM
    Author     : siech
--%>

<%@tag description="This is the parent tag for java server pages used in Ocena" pageEncoding="UTF-8"%>

<%-- The list of normal attributes:--%>
<%@attribute name="title" required="true" %>

<%-- Navigation menu items go here --%>
<%@attribute name="menuitems" required="true" %>

<%-- The list of fragments:--%>
<%@attribute name="content" fragment="true"%>

<%-- Recursive html content is specified below: --%>

<!DOCTYPE html>
<html>
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

                $("#accordion").accordion({header: "h1", collapsible: true, active: false, heightStyle: "content"});

                $("#add-start-date").datepicker();
                $("#add-end-date").datepicker();
                $(".start-date").datepicker();
                $(".end-date").datepicker();

                $("#admission-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function () {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).val($.datepicker.formatDate('M yy', new Date(year, month, 1)));
                    }
                });

                $("#admission-year").focus(function () {
                    $(".ui-datepicker-calendar").hide();
                    $(".ui-datepicker-current").hide();
                    $("#ui-datepicker-div").position({
                        my: "center top",
                        at: "center bottom",
                        of: $(this)
                    });
                });

                $("#edit-admission-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function () {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).val($.datepicker.formatDate('M yy', new Date(year, month, 1)));
                    }
                });

                $("#edit-admission-year").focus(function () {
                    $(".ui-datepicker-calendar").hide();
                    $(".ui-datepicker-current").hide();
                    $("#ui-datepicker-div").position({
                        my: "center top",
                        at: "center bottom",
                        of: $(this)
                    });
                });

                $(".admission-month-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function () {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).val($.datepicker.formatDate('M yy', new Date(year, month, 1)));
                    }
                });
                $(".admission-month-year").focus(function () {
                    $(".ui-datepicker-calendar").hide();
                    $(".ui-datepicker-current").hide();
                    $("#ui-datepicker-div").position({
                        my: "center top",
                        at: "center bottom",
                        of: $(this)
                    });
                });

                $("#add-admission-month-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function () {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).val($.datepicker.formatDate('M yy', new Date(year, month, 1)));
                    }
                });

                $("#add-admission-month-year").focus(function () {
                    $(".ui-datepicker-calendar").hide();
                    $(".ui-datepicker-current").hide();
                    $("#ui-datepicker-div").position({
                        my: "center top",
                        at: "center bottom",
                        of: $(this)
                    });
                });

                $("#edit-admission-month-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function () {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).val($.datepicker.formatDate('M yy', new Date(year, month, 1)));
                    }
                });

                $("#edit-admission-month-year").focus(function () {
                    $(".ui-datepicker-calendar").hide();
                    $(".ui-datepicker-current").hide();
                    $("#ui-datepicker-div").position({
                        my: "center top",
                        at: "center bottom",
                        of: $(this)
                    });
                });
            });

        </script>

    </head>

    <body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top" onload="checkEvaluationSession()">

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

                        ${menuitems} 

                    </ul>

                </div>
                <!-- /.navbar-collapse -->

            </div>
            <!-- /.container -->

        </nav>

        <div>

            <jsp:invoke fragment="content" />

        </div>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="static/plugins/jquery/jquery.min.js"></script>
        <script src="static/plugins/bootstrap/bootstrap.min.js"></script>
        <script src="static/plugins/i18n/jquery.i18n.properties.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="static/plugins/ie/ie10-viewport-bug-workaround.js"></script>
        <script src="static/plugins/jquery-ui/jquery-ui.js"></script>
        <script src="static/js/actions.js"></script>

    </body>

</html>