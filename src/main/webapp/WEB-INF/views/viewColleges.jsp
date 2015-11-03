<%-- 
    Document   : viewColleges
    Created on : Jun 4, 2015, 5:29:32 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:main-admin>
    <jsp:attribute name="title"> Ocena - view colleges </jsp:attribute>
    <jsp:attribute name="content">
        <h1>Colleges in ${institution.name}</h1>

        <table id="college-table" class="table table-responsive table-hover parent-table">
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
                    <td colspan="5"> List of colleges</td>
                </tr>
            </tfoot>
            <tbody>    
                <c:forEach var="college" items="${sessionScope.colleges}" varStatus="index">
                    <tr <c:if test="${index.count % 2 == 0}"> class="even" </c:if> >
                        <td onclick="loadWindow('/Ocena/retrieveFaculties?collegeId=${college.id}')">${index.count}</td>
                        <td onclick="loadWindow('/Ocena/retrieveFaculties?collegeId=${college.id}')">${college.name}</td>
                        <td onclick="loadWindow('/Ocena/retrieveFaculties?collegeId=${college.id}')">${college.abbreviation}</td>
                        <td><button onclick="editCollege('${college.id}', '${college.name}', '${college.abbreviation}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                        <td><button onclick="removeCollege('${college.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="pull-right">
            <button type="button" class="btn btn-default" onclick="addCollege();
                    return false;"> Add </button>
        </div>
        <div class="dialog" id="college-dialog">
            <table>
                <tr>
                    <td> Name </td>
                    <td> <input type="text" id="college-name"></td>
                </tr>
                <tr>
                    <td> Abbreviation </td>
                    <td> <input type="text" id="college-abbreviation"></td>
                </tr>
            </table>
        </div>
    </jsp:attribute>
</ocena:main-admin>
