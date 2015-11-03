<%-- 
    Document   : slidingforms
    Created on : Jun 8, 2015, 8:32:26 PM
    Author     : Ben Siech
--%>

<%@tag description="This is the parent tag for java server pages that have a sliding form" pageEncoding="UTF-8"%>

<%-- The list of normal attributes:--%>
<%@attribute name="title" required="true" %>

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
        <link rel="icon" href="../../favicon.ico">

        <title>${title}</title>

        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

        <!-- Bootstrap core CSS -->
        <link href="static/plugins/bootstrap/bootstrap.min.css" rel="stylesheet" />

        <!-- Jquery ui CSS -->
        <link href="static/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />

        <link href="static/plugins/jquery-ui/jquery-ui.css" rel="stylesheet" />

        <!-- Custom styles for this template -->
        <link href="static/css/style.css" rel="stylesheet">

        <script>
            $(function () {
                $("#admission-year").datepicker({
                    dateFormat: 'M yy',
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    onClose: function (dateText, inst) {
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
            });
        </script>

    </head>
    <body>

        <div>
            <div id="content">
                <div id="wrapper">
                    <jsp:invoke fragment="content"/>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript
      ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="static/plugins/jquery/jquery.min.js"></script>
        <script src="static/plugins/i18n/jquery.i18n.properties.min.js"></script>
        <!-- Custom javascript -->
        <script src="static/plugins/sliding-form/sliding.form.js"></script>
        <script src="static/plugins/jquery-ui/jquery-ui.min.js"></script>    
        <script src="static/js/actions.js"></script>

    </body>
</html>
