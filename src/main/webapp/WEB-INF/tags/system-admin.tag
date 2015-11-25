<%-- 
    Document   : admin
    Created on : Nov 25, 2015, 9:14:17 PM
    Author     : siech
--%>

<%@tag description="This is the parent tag for java server pages used by the system admin" pageEncoding="UTF-8"%>

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
            <button class="btn btn-default dropdown-toggle" type="button" id="institution-dropdown" data-toggle="dropdown" aria-expanded="true">
                Settings
            </button>  
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/institution">Institution</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-2" href="/Ocena/retrieveColleges">Colleges</a></li>
            </ul>
        </li>
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
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/createAccountAtMainAdmin">Add user</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="/Ocena/adminUserView">Upgrade user</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="institution-dropdown" data-toggle="dropdown" aria-expanded="true">
                Reports
            </button>  
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-2" href="/Ocena/downloadAllReports">Download reports</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true">
                Account
            </button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-2" href="/Ocena/logout"> Logout </a></li>
                <li role="presentation"><a role="menuitem" tabindex="-2" href="/Ocena/viewCredentials"> Credentials </a></li>
            </ul>
        </li>

    </jsp:attribute>

    <jsp:attribute name="content"> <jsp:invoke fragment="content" /> </jsp:attribute>

</ocena:genericpage>