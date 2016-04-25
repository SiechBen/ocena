<%-- 
    Document   : lecturer
    Created on : Aug 13, 2015, 6:21:50 AM
    Author     : Ben Siech
--%>

<%@tag description="This is the parent tag for java server pages used by the student" pageEncoding="UTF-8"%>

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
                <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/viewUserProfile')">
                    Profile
                </button>
            </li>
            <li class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" id="evaluation-dropdown" data-toggle="dropdown" aria-expanded="true" onclick="loadWindow('/Ocena/logout')">
                    Logout
                </button>
            </li>

        </jsp:attribute>

        <jsp:attribute name="content"> <jsp:invoke fragment="content" /> </jsp:attribute>

    </ocena:genericpage>
