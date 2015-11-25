<%-- 
    Document   : facultiesAndDepartments
    Created on : Jun 10, 2015, 12:10:19 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - view faculties </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1>Faculties in ${sessionScope.college.name}</h1>

                <table id="faculty-table" class="table table-responsive table-hover parent-table">
                    <thead>
                        <tr>
                            <th>&nbsp;</th>
                            <th>Name</th>
                            <th>Abbreviation</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td colspan="6"> List of faculties in ${sessionScope.college.name}</td>
                        </tr>
                    </tfoot>
                    <tbody>    
                        <c:forEach var="faculty" items="${sessionScope.faculties}" varStatus="index">
                            <tr <c:if test="${index.count % 2 == 0}"> class="even" </c:if> >
                                <td onclick="loadWindow('/Ocena/checkDepartment?facultyId=${faculty.id}&collegeId=${sessionScope.college.id}')">${index.count}</td>
                                <td onclick="loadWindow('/Ocena/checkDepartment?facultyId=${faculty.id}&collegeId=${sessionScope.college.id}')">${faculty.name}</td>
                                <td onclick="loadWindow('/Ocena/checkDepartment?facultyId=${faculty.id}&collegeId=${sessionScope.college.id}')">${faculty.abbreviation}</td>
                                <td><button onclick="loadWindow('/Ocena/editFaculty?facultyId=${faculty.id}&collegeId=${sessionScope.college.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                <td><button onclick="removeFaculty('${faculty.id}', '${sessionScope.college.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="pull-right">
                    <button type="button" class="btn btn-default" onclick="addFaculty('${sessionScope.college.id}', '${sessionScope.college.name}');
                    return false;"> Add </button>
                </div>
                <div class="dialog" id="faculty-dialog">
                    <table>
                        <tr>
                            <td colspan="2">
                                <b> Faculty details </b>
                            </td>
                        </tr>
                        <tr>
                            <td> Name </td>
                            <td> <input type="text" id="faculty-name"></td>
                        </tr>
                        <tr>
                            <td> Abbreviation </td>
                            <td> <input type="text" id="faculty-abbreviation"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <b> Email contact </b>
                            </td>
                        </tr>
                        <tr>
                            <td> Email address </td>
                            <td> <input type="text" id="faculty-email"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <b>Phone contact</b>
                            </td>
                        </tr>
                        <tr>
                            <td> Mobile number </td>
                            <td> <input type="text" id="faculty-mobile-number"></td>
                        </tr>
                        <tr>
                            <td> Fixed number </td>
                            <td> <input type="text" id="faculty-fixed-number"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <b> Postal contact</b>
                            </td>
                        </tr>
                        <tr>
                            <td> Box number </td>
                            <td> <input type="text" id="faculty-box-number"></td>
                        </tr>
                        <tr>
                            <td> Postal code </td>
                            <td> <input type="text" id="faculty-postal-code"></td>
                        </tr>
                        <tr>
                            <td> Town </td>
                            <td> <input type="text" id="town"></td>
                        </tr>
                        <tr>
                            <td> Country </td>
                            <td> 
                                <select id="country" name="country" style="max-width: 180px;">
                                    <c:forEach var="country" items="${applicationScope.countries}">
                                        <option value="${country.id}">${country.name}</option>
                                    </c:forEach>
                                </select> 
                            </td>
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
