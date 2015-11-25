<%-- 
    Document   : viewInstitution
    Created on : Jun 4, 2015, 5:29:32 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - view institution </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1>${applicationScope.institution.name}</h1>
                <table id="institution-table">
                    <thead>
                        <tr>
                            <th colspan="3"></th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr colspan="3">
                            <td>${applicationScope.institution.abbreviation} is a world class University</td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <tr>
                            <td>Name:</td>
                            <td>${applicationScope.institution.name}</td>
                        </tr>
                        <tr>
                            <td>Abbreviation:</td>
                            <td>${applicationScope.institution.abbreviation}</td>

                        </tr>
                        <tr>
                            <td>Country:</td>
                            <td>${applicationScope.institution.country.name}</td>
                        </tr>
                        <tr>
                            <td><button onclick="editInstitution('${applicationScope.institution.name}', '${applicationScope.institution.abbreviation}', '${applicationScope.institution.country.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button> Edit institution details</td>
                            <td><button onclick="removeInstitution()"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button> Remove the institution</td>        
                        </tr>
                    </tbody>
                </table>
                <div class="dialog" id="institution-dialog">
                    <table>
                        <tr>
                            <td> Name </td>
                            <td> <input type="text" id="institution-name"></td>
                        </tr>
                        <tr>
                            <td> Abbreviation </td>
                            <td> <input type="text" id="institution-abbreviation"></td>
                        </tr>
                        <td>
                            <label for="country">Country</label>
                        </td>
                        <td> 
                            <select id="country" name="country">
                                <c:forEach var="country" items="${applicationScope.countries}">
                                    <option value="${country.id}">${country.name}</option>
                                </c:forEach>
                            </select> 
                        </td>
                    </table>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>
    </div>

</jsp:attribute>
</ocena:system-admin>