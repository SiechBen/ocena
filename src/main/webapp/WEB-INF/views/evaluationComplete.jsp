<%-- 
    Document   : evaluationComplete
    Created on : Aug 3, 2015, 6:42:00 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:evaluatorpage>

    <jsp:attribute name="title">
        Ocena - evaluation complete
    </jsp:attribute>

    <jsp:attribute name="content">

        <div>
            <p></br>${sessionScope.person.firstName}, below is feedback for all evaluations you've done</p>
            <h1>Latest evaluation feedback</h1>
            <div id="current-feedback">
                <p class="heading">${sessionScope.currentMessage} <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${sessionScope.currentFeedback.dateCompleted}"/> </p>
                <p>${sessionScope.currentFeedback.feedback}</p>
            </div>
            <h1>Other evaluation feedbacks</h1>
            <div id="accordion">
                <c:forEach var="feedback" items="${sessionScope.feedbacks}">
                    <h1> <fmt:formatDate type="date" dateStyle="full" value="${feedback.dateCompleted}"/> </h1>
                    <div>
                        <p class="heading">${sessionScope.otherMessage} <fmt:formatDate type="both" dateStyle="default" timeStyle="default" value="${feedback.dateCompleted}"/> </p>
                        <p>${feedback.feedback}</p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </jsp:attribute> 
</ocena:evaluatorpage>