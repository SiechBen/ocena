<%-- 
    Document   : viewCourses
    Created on : Jun 29, 2015, 1:20:29 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>

<ocena:main-admin>

    <jsp:attribute name="title" > Ocena - view courses </jsp:attribute>
    <jsp:attribute name="content">
        <h1> Courses in ${sessionScope.degree.name} </h1>
        <div>
            <table id="course-table" class="table table-responsive table-hover parent-table">
                <thead>
                    <tr>
                        <th> &nbsp; </th>
                        <th> Title </th>
                        <th> Code </th>
                        <th> &nbsp; </th>
                        <th> &nbsp; </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="5"> Course </td>
                    </tr>
                </tfoot>
                <tbody>
                    <c:forEach var="course" items="${sessionScope.courses}" varStatus="pos">
                        <tr>
                            <td> ${pos.count} </td>
                            <td> ${course.title} </td>
                            <td> ${course.code} </td>
                            <td><button onclick="editCourse('${course.id}', '${course.title}', '${course.code}', '${sessionScope.degree.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                            <td><button onclick="removeCourse('${course.id}', '${sessionScope.degree.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="pull-right">
                <button class="btn btn-default" onclick="addCourse('${sessionScope.degree.id}');
                        return false;"> Add </button>
            </div>
        </div>
        <div class="dialog" id="course-dialog">
            <table>
                <tr>
                    <td> Course title </td>
                    <td> <input type="text" id="course-title" required="true"></td>
                </tr>
                <tr>
                    <td> Course code </td>
                    <td> <input type="text" id="course-code" required="true"></td>
                </tr>
            </table>
        </div>             
    </jsp:attribute>
</ocena:main-admin>