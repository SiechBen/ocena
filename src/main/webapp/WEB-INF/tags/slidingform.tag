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
        <!-- Custom CSS -->
        <link href="static/css/style.css" rel="stylesheet">
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
        <!-- Custom javascript -->
        <script src="static/plugins/sliding-form/sliding.form.js"></script>
        <script src="static/js/actions.js"></script>

    </body>
</html>
