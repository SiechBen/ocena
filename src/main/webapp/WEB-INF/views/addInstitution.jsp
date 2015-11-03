<%-- 
    Document   : institution
    Created on : Jun 4, 2015, 5:29:32 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:slidingform>
    <jsp:attribute name="title"> Ocena - add institution </jsp:attribute>
    <jsp:attribute name="content">
        
          <h1>Add an institution</h1>
          
          <div id="steps">
            <form id="add-institution-form" name="add-institution-form" action="/Ocena/addInstitution" method="post">
                <fieldset class="step">
                    <legend>Set up institution</legend>
                    <p>
                        <label for="country">Country</label>
                        <select id="country" name="country">
                            <c:forEach var="country" items="${applicationScope.countries}">
                                <option value="${country.id}">${country.name}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label for="institution-name">Name</label>
                        <input id="institution-name" name="institution-name" type="text" />
                    </p>
                    <p>
                        <label for="abbreviation">Abbreviation</label>
                        <input id="abbreviation" name="abbreviation" type="text" />
                    </p>
                </fieldset>

                <fieldset class="step">
                    <legend>Confirm and submit</legend>
                    <p>
                        The institution is going to be set up using the details you provided
                    </p>
                    <p class="submit">
                        <button id="setup-button" type="submit">Set up</button>
                    </p>
                </fieldset>
            </form>
        </div>
        <div id="navigation" style="display:none;">
            <ul>
                <li class="selected">
                    <a href="#">Institution</a>
                </li>
                <li>
                    <a href="#">Confirm</a>
                </li>
            </ul>
        </div>
    </jsp:attribute>
</ocena:slidingform>