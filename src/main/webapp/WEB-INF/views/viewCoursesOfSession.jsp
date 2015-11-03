<%-- 
    Document   : viewCourseOfSession
    Created on : Jul 14, 2015, 8:47:16 AM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:sub-admin>
    <jsp:attribute name="title"> Ocena - view course-of-sessions </jsp:attribute>
    <jsp:attribute name="content">
        <h1>Courses of current evaluation session</h1>

        <table id="course-of-session-table" class="table table-responsive table-hover parent-table">
            <thead>
                <tr>
                    <th>&nbsp;</th>
                    <th>Course</th>
                    <th>Tutor/lecturer</th>
                    <th>&nbsp;</th>
                    <th>&nbsp;</th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <td colspan="5"> Course-lecturer combination</td>
                </tr>
            </tfoot>
            <tbody>    
                <c:forEach var="courseOfSession" items="${sessionScope.coursesOfSession}" varStatus="index">
                    <tr <c:if test="${index.count % 2 == 0}"> class="even" </c:if> >
                        <td>${index.count}</td>
                        <td>${courseOfSession.course.code} - ${courseOfSession.course.title}</td>
                        <td>${sessionScope.personByCourseOfSessionMap.get(courseOfSession).referenceNumber} - ${sessionScope.personByCourseOfSessionMap.get(courseOfSession).firstName} ${sessionScope.personByCourseOfSessionMap.get(courseOfSession).lastName}</td>
                        <td><button onclick="editCourseOfSession('${courseOfSession.id}', '${courseOfSession.facultyMember.id}', '${courseOfSession.course.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                        <td><button onclick="removeCourseOfSession('${courseOfSession.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <input type="hidden" id="course-of-session-evaluation-session" value="${sessionScope.evaluationSession.id}">
        <div class="pull-right">
            <button type="button" class="btn btn-default" onclick="addCourseOfSession();
                    return false;"> Add </button>
        </div>
        <div class="dialog" id="course-of-session-dialog">
            <table>
                <tr>
                    <td> Select course </td>
                    <td>
                        <select id="course-of-session-course">
                            <c:forEach var="course" items="${sessionScope.courses}">
                                <option value="${course.id}">${course.code} - ${course.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td> Match tutor/lecturer </td>
                    <td> 
                        <select id="course-of-session-lecturer">
                            <c:forEach var="facultyMember" items="${sessionScope.facultyMembers}">
                                <option value="${facultyMember.id}">${sessionScope.personByFacultyMemberMap.get(facultyMember).referenceNumber} - ${sessionScope.personByFacultyMemberMap.get(facultyMember).firstName} &nbsp; ${sessionScope.personByFacultyMemberMap.get(facultyMember).lastName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </jsp:attribute>
</ocena:sub-admin>
