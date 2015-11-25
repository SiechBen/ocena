<%-- 
    Document   : evaluationArena
    Created on : Jul 29, 2015, 8:34:21 AM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:evaluator>
    <jsp:attribute name="title"> Ocena - perform evaluation </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <div class="content-container">

                    <div id="course-evaluated-info">
                        <p> ${sessionScope.courseEvaluationInfo} </p>
                    </div> 
                    <div id="evaluation-questions-holder">
                        <h1> Select a course to evaluate upon </h1>
                        <div>
                            <span id="admission-holder">
                                <label for="admission-dropdown"> Admission </label>
                                <select name="admission-dropdown" id="admission-dropdown" onchange="updateDegrees('${sessionScope.faculty.id}', '${sessionScope.department.id}')" class="select2-default">
                                    <option disabled selected> Select admission </option>
                                    <c:forEach var="admission" items="${sessionScope.admissions}">
                                        <option value="${admission.id}"> ${admission.admission} </option>
                                    </c:forEach>
                                </select>
                            </span>
                            <span id="degree-holder">
                                <label for="degree-dropdown"> Degree </label>
                                <select name="degree-dropdown" id="degree-dropdown" onchange="updateCourses()" class="select2-default">
                                    <option disabled selected> Select degree </option>
                                </select>
                            </span>
                            <span id="course-holder">
                                <label for="course-dropdown"> Course </label>
                                <select name="course-dropdown" id="course-dropdown" onchange="setCourse()" class="select2-default">
                                    <option disabled selected> Select course </option>
                                </select>
                            </span>
                            <span>
                                <button onclick="displayEvaluationQuestions('${sessionScope.faculty.id}', '${sessionScope.department.id}')" class="btn btn-default"> Proceed </button>
                            </span>  
                        </div>
                    </div>
                    &nbsp;
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute> 
</ocena:evaluator>