<%-- 
Document : addUSer
Created on : Jun 6, 2015, 7:21:59 PM
Author  : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:sub-admin>
    <jsp:attribute name="title"> Ocena - evaluation session </jsp:attribute>
    <jsp:attribute name="content">
        <h1> Evaluation session </h1>
       <table>
            <tr>
                <td> <button class="btn btn-default pull-left" onclick="loadWindow('/Ocena/addEvaluationSession')"> Add an evaluation session </button></td>
            </tr>
        </table>
        <table id="evaluation-session-table">
            <thead>
                <tr>
                    <th colspan="2"> SETUP EVALUATION SESSION </th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <td colspan="2"> <button type="text" id="setup-evaluation-session" class="btn btn-default" onclick="setupEvaluationSession()"> Set this session up </button> </td>
                </tr>
            </tfoot>
            <tbody>
        </table>
        <input type="hidden" name="facultyId" id="facultyId" value="${sessionScope.faculty.id}">
        <input type="hidden" name="departmentId" id="departmentId" value="${sessionScope.department.id}">
         </jsp:attribute>
</ocena:sub-admin>