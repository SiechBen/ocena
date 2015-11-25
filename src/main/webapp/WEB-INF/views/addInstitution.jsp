<%-- 
    Document   : addinstitution
    Created on : Jun 4, 2015, 5:29:32 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - add institution </jsp:attribute>
    <jsp:attribute name="content">

        <div id="slider-content">
            <div id="slider-wrapper">
                <h1>Add an institution</h1>
                <div id="steps">
                    <form id="add-institution-form" name="add-institution-form" class="sliding-form" action="/Ocena/addInstitution" method="post">
                        <fieldset class="step">
                            <legend>Set up institution</legend>
                            <p>
                                <label for="country">Country</label>
                                <select id="country" name="country" required="true">
                                    <c:forEach var="country" items="${applicationScope.countries}">
                                        <option value="${country.id}">${country.name}</option>
                                    </c:forEach>
                                </select>
                            </p>
                            <p>
                                <label for="institution-name">Name</label>
                                <input id="institution-name" name="institution-name" type="text" required="true" />
                            </p>
                            <p>
                                <label for="abbreviation">Abbreviation</label>
                                <input id="abbreviation" name="abbreviation" type="text" required="true" />
                            </p>
                        </fieldset>

                        <fieldset class="step">
                            <legend>Confirm and submit</legend>
                            <p>
                                The institution is going to be set up using the details you provided
                            </p>
                            <p class="submit">
                                <button id="setup-button" type="submit" class="btn btn-default slider-submit-button">Set up</button>
                            </p>
                            <legend>Abort</legend>
                            <p>
                                Click the button below to abort this mission
                            </p>
                            <p class="central">
                                <button id="abort-button" type="button"  class="btn btn-default" onclick="loadWindow('/Ocena/home')">Abort</button>
                        </fieldset>
                    </form>
                </div>
                <div id="slider-navigation" style="display:none;">
                    <ul>
                        <li class="selected">
                            <a href="#">Institution</a>
                        </li>
                        <li>
                            <a href="#">Confirm/abort</a>
                        </li>
                    </ul>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:system-admin>