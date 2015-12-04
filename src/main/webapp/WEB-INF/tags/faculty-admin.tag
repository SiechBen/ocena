<%-- 
    Document   : faculty-admin
    Created on : Nov 25, 2015, 9:25:32 PM
    Author     : siech
--%>

<%@tag description="This is the parent tag for java server pages used by the faculty/department admin" pageEncoding="UTF-8"%>

<%-- The list of normal attributes:--%>
<%@attribute name="title" required="true" %>

<%-- The list of fragments:--%>
<%@attribute name="content" fragment="true"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:genericpage>

    <jsp:attribute name="title"> ${title} </jsp:attribute>

    <jsp:attribute name="menuitems"> 

        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/home')">
                Home
            </button>
        </li>
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true">
                Users
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/createAccountAtSubAdmin">Add user</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/viewUser">Upgrade user</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/viewAdminProfile">Edit my profile</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="institution-dropdown" data-toggle="dropdown" aria-expanded="true">
                Evaluation
            </button>  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/viewEvaluationSessions?facultyId=${sessionScope.faculty.id}&departmentId=${sessionScope.department.id}">View evaluation sessions</a></li>
            </ul>
        </li>   
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="institution-dropdown" data-toggle="dropdown" aria-expanded="true">
                Reports
            </button>  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-2" href="/Ocena/downloadFacultyReports">Download reports</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/logout')">
                Logout
            </button>
        </li>

    </jsp:attribute>

    <jsp:attribute name="content"> <jsp:invoke fragment="content" /> </jsp:attribute>

</ocena:genericpage>