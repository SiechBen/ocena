<%-- 
    Document   : viewUsers
    Created on : Apr 9, 2016, 10:14:51 AM
    Author     : siech
--%>

<%@page import="ke.co.miles.ocena.utilities.UserGroupDetail"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>

<ocena:system-admin>

    <jsp:attribute name="title"> Ocena - view persons </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1>All ${institution.name} users of Ocena</h1>

                <table id="person-table" class="table table-responsive table-hover parent-table">
                    <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th>Name</th>
                            <th>Reference/registration no.</th>
                            <th>National id/passport</th>
                            <th>User group</th>
                            <th>Faculty member role</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td colspan="5"> List of people</td>
                        </tr>
                    </tfoot>
                    <tbody>    
                        <c:forEach var="person" items="${sessionScope.usersMap.keySet()}" varStatus="index">
                            <tr <c:if test="${index.count % 2 == 0}"> class="even" </c:if> >
                                <td>${index.count}</td>
                                <td>${person.firstName} ${person.lastName}</td>
                                <td>${person.referenceNumber}</td>
                                <td>${person.nationalIdOrPassport}</td>
                                <c:forEach var="usergroup" items="${usersMap.get(person).keySet()}"> 
                                    <td>${usergroup.userGroup}</td>
                                    <td>${usersMap.get(person).get(usergroup).facultyMemberRole}</td>
                                </c:forEach>
                                <td><button onclick="editPerson('${person.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                <td><button onclick="removePerson('${person.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="pull-right">
                    <button class="btn btn-default" onclick="loadWindow('/Ocena/createAccountAtMainAdmin')"> Add person </button>
                </div>
                <div class="dialog" id="person-dialog">
                    <table>
                        <tr>
                            <td> First name </td>
                            <td> <input type="text" id="person-first-name"></td>
                        </tr>
                        <tr>
                            <td> Last name </td>
                            <td> <input type="text" id="person-last-name"></td>
                        </tr>
                        <tr>
                            <td> Ref/reg number </td>
                            <td> <input type="text" id="person-reference-number"></td>
                        </tr>
                    </table>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>
    </jsp:attribute>

</ocena:system-admin>