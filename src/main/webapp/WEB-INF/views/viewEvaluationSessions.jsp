<%-- 
Document : viewEvaluationSessions
Created on : Jun 6, 2015, 7:21:59 PM
Author  : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:faculty-admin>
    <jsp:attribute name="title"> Ocena - evaluation session </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1> Evaluation session </h1>
                <table>
                    <tr>
                        <td> <button class="btn btn-default pull-left" onclick="loadWindow('/Ocena/addEvaluationSession')"> Add an evaluation session </button></td>
                    </tr>
                </table>

                <table id="evaluation-session-table">
                    <thead>
                        <tr>
                            <th colspan="2"> ACTIVE EVALUATION SESSIONS </th>
                        </tr>
                    </thead>

                    <tfoot>
                        <tr>
                            <td colspan="2"> Evaluation sessions </td>
                        </tr>
                    </tfoot>

                    <tbody>
                        <c:forEach var="evaluationSession" items="${sessionScope.evaluationSessions}" varStatus="pos">
                            <tr> <th> </th> </tr>
                            <tr> 
                                <td> Degree: </td>
                                <td>
                                    <select class="course-of-session-degree">
                                        <c:forEach var="degree" items="${sessionScope.degrees}">
                                            <option value="${degree.id}" <c:if test="${degree.id} == ${evaluationSession.degree.id}"> selected </c:if>> ${degree.name} </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td> Start date: </td>
                                <td> <input readonly="true"  type="date" class="start-date" value="<fmt:formatDate pattern="MM/dd/yyyy" value='${evaluationSession.startDate}' />" > </td>
                            </tr>
                            <tr>
                                <td> End date: </td>
                                <td> 
                                    <input readonly="true"  type="date" class="end-date" value="<fmt:formatDate pattern="MM/dd/yyyy" value='${evaluationSession.endDate}' />" > 
                                </td>
                            </tr>
                            <tr>
                                <td> Academic year </td>
                                <td> <input readonly="true"  type="text" class="academic-year" value="${evaluationSession.academicYear}" /> </td>
                            </tr>

                            <tr>
                                <td> Semester: </td>
                                <td> <input readonly="true"  type="text" id="semester" value="${evaluationSession.semester}" > </td>
                            </tr>
                            <tr>
                                <td> Admission month & year </td>
                                <td> <input readonly="true"  type="date" class="admission-month-year" value="<fmt:formatDate pattern="MMM yyyy" value='${evaluationSession.admissionYear}' />" /> </td>
                            </tr> 
                            <tr>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <td> <button id="view-courses-of-session-button" class="btn btn-default" onclick="loadWindow('/Ocena/retrieveCoursesOfSession?evaluationSessionId=${evaluationSession.id}&degreeId=${evaluationSession.degree.id}&facultyId=${sessionScope.faculty.id}&departmentId=${sessionScope.department.id}')"> Courses of this session </button></td>
                                <td>
                                    <button class=" btn btn-default edit-evaluation-session-button" onclick="editEvaluationSession('${evaluationSession.degree.id}', '<fmt:formatDate pattern="MM/dd/yyyy" value="${evaluationSession.startDate}" />', '<fmt:formatDate pattern="MM/dd/yyyy" value="${evaluationSession.endDate}" />', '${evaluationSession.academicYear}', '${evaluationSession.semester}', '<fmt:formatDate pattern="MMM yyyy" value="${evaluationSession.admissionYear}" />', '${evaluationSession.id}')"> Edit this session </button>
                                    <button class=" btn btn-default close-evaluation-session-button" onclick="closeEvaluationSession('${evaluationSession.id}')"> Close this session </button> 
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <input readonly="true"  type="hidden" name="facultyId" id="facultyId" value="${sessionScope.faculty.id}">
                <input readonly="true"  type="hidden" name="departmentId" id="departmentId" value="${sessionScope.department.id}">
                <div class="dialog" id="evaluation-session-dialog">
                    <table>
                        <tr>
                            <td> Degree: </td>
                            <td>
                                <select id="evaluation-session-degree" required="true">
                                    <c:forEach var="degree" items="${sessionScope.degrees}">
                                        <option value="${degree.id}" selected>${degree.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td> Start date </td>
                            <td> 
                                <input id="evaluation-session-start-date" class="start-date" required="true" onchange="checkDate();
                                        return false;">
                            </td>
                        </tr>
                        <tr>
                            <td> End date </td>
                            <td> 
                                <input id="evaluation-session-end-date"  class="end-date" required="true" onchange="checkDate();
                                        return false;">
                                <span id="information-box" class="alert alert-warning" hidden></span> 
                            </td>
                        </tr>
                        <tr>
                            <td> Academic year </td>
                            <td> <input type="text" id="evaluation-session-academic-year" required="true"></td>
                        </tr>
                        <tr>
                            <td> Semester </td>
                            <td> <input type="text" id="evaluation-session-semester" required="true"></td>
                        </tr>
                        <tr>
                            <td> Admission month & year </td>
                            <td> <input id="evaluation-session-admission-year" class="admission-month-year" required="true"></td>
                        </tr>
                    </table>
                </div>   
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:faculty-admin>