<%-- 
    Document   : lecturerDashboard
    Created on : Aug 13, 2015, 6:20:52 AM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:lecturer>
    <jsp:attribute name="title"> Ocena - lecturer dashboard </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute> 
</ocena:lecturer>
