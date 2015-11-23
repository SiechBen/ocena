<%-- 
    Document   : addEvaluationSession
    Created on : Jul 16, 2015, 9:24:54 AM
    Author     : Ben Siech

<%@page contentType="text/html" pageEncoding="UTF-8"%>
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:sub-admin>
    <jsp:attribute name="title"> Ocena - add evaluation session </jsp:attribute>
    <jsp:attribute name="content">
        <h1> Evaluation session </h1>
        <table>
            <tr>
                <td> <button class="btn btn-default pull-left" onclick="loadWindow('/Ocena/addEvaluationSession')"> Add an evaluation session </button></td>
            </tr>
        </table>
        <table id="add-evaluation-session-table">
            <thead>
                <tr>
                    <th colspan="2"> SETUP EVALUATION SESSION </th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <td colspan="2"> <button type="text" id="add-setup-evaluation-session" class="btn btn-default" onclick="addEvaluationSession()"> Set this session up </button> </td>
                </tr>
            </tfoot>
            <tbody>
                <tr> 
                    <td> Degree: </td>
                    <td>
                        <select id="add-course-of-session-degree">
                            <c:forEach var="degree" items="${sessionScope.degrees}">
                                <option value="${degree.id}" selected>${degree.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td> Start date: </td>
                    <td> <input type="text" id="add-start-date"> </td>
                </tr>
                <tr>
                    <td> End date: </td>
                    <td> 
                        <input type="text" id="add-end-date" onchange="checkAddDate();
                                return false;"> 
                        <span id="add-information-box" class="alert alert-warning" hidden></span> 
                    </td>
                </tr>
                <tr>
                    <td> Academic year </td>
                    <td> <input type="date" id="add-academic-year"/> </td>
                </tr>

                <tr>
                    <td> Semester: </td>
                    <td> <input type="text" id="add-semester"> </td>
                </tr>
                <tr>
                    <td> Admission month & year </td>
                    <td> <input type="date" id="add-admission-month-year"/> </td>
                </tr> 
            </tbody>
            <input type="hidden" name="facultyId" id="add-facultyId" value="${sessionScope.faculty.id}">
            <input type="hidden" name="departmentId" id="add-departmentId" value="${sessionScope.department.id}">
        </table>

    </jsp:attribute>
</ocena:sub-admin>