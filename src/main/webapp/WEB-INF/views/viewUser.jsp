<%-- 
    Document   : institution
    Created on : Jun 4, 2015, 5:29:32 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:faculty-admin>
    <jsp:attribute name="title"> Ocena - upgrade user </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1> Retrieve and upgrade a user </h1>
                <div id="upgrade-user-holder">
                    <div id="upgrade-user-number-holder">
                        <label for="upgrade-user-number">User reg/staff number: </label>
                        <input type="text" id="upgrade-user-number" name="upgrade-user-number">
                        <button id="upgrade-retrieve-button" class="btn btn-default" onclick="retrieveUser();
                                return false;"> Retrieve user </button>
                    </div>
                    <div id="upgrade-user-details-holder">
                    </div>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:faculty-admin>