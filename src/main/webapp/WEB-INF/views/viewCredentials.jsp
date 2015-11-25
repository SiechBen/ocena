<%-- 
    Document   : viewCredentials
    Created on : Nov 11, 2015, 8:18:01 PM
    Author     : siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - view admin user credentials </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1> ${sessionScope.adminCredentials.username} user credentials </h1>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th colspan="2"></th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="2"> Admin user credentials </td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <tr>
                                <td> Username: </td>
                                <td>${sessionScope.adminCredentials.username}</td>
                            </tr>
                            <tr>    
                                <td colspan="2"><button id="edit-overall-admin-button" onclick="editAdminCredentials()"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit admin credentials</button> </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div id="edit-overall-admin-div">
                    <form id="edit-overall-admin-form" action="/Ocena/editOverallAdminCredentials" method="POST"> 
                        <table id="credentials-table">
                            <thead>
                                <tr>
                                    <th colspan="3"></th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="3"> Edit admin user credentials </td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <tr>
                                    <td> Username: </td>
                                    <td> <input type="text" id="overall-admin-username" name="overall-admin-username" value="${sessionScope.adminCredentials.username}" required="true"> </td>
                                </tr>
                                <tr>
                                    <td> Old password: </td>
                                    <td> <input type="password" id="old-overall-admin-password" name="old-overall-admin-password" value="unset" onchange="validateOverallAdminPassword()" /> </td>
                                    <td id="valid-overall-admin-password-information"></td>
                                </tr>
                                <tr>
                                    <td> New password: </td>
                                    <td> <input type="password"  id="new-overall-admin-password" name="new-overall-admin-password"/> </td>
                                </tr>
                                <tr>
                                    <td> Confirm password: </td>
                                    <td> <input type="password" id="confirm-overall-admin-password" name="confirm-overall-admin-password" onchange="matchOverallAdminPassword()"/> </td>
                                    <td id="matching-overall-admin-password-information"></td>
                                </tr>
                                <tr>
                                    <td colspan="3"><input id="submit-overall-admin-button" name="submit-overall-admin-button" type="submit" class="btn btn-default" value="Click to confirm edit"></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:system-admin>