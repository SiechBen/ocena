<%-- 
    Document   : managementDashboard
    Created on : Apr 8, 2016, 2:00:56 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>

<ocena:lecturer>
    <jsp:attribute name="title"> Ocena - management dashboard </jsp:attribute>
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
