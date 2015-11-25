<%-- 
    Document   : adminDashboard
    Created on : Jun 3, 2015, 9:08:30 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - admin dashboard </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:system-admin>>