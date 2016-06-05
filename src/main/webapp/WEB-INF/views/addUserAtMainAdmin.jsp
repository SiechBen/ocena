<%-- 
    Document   : addUserMainAdmin
    Created on : Nov 23, 2015, 12:50:21 PM
    Author     : siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - add a user </jsp:attribute>
    <jsp:attribute name="content">

        <div id="slider-content">
            <div id="slider-wrapper">
                <h1> Add a user </h1>
                <div id="steps">
                    <form id="create-account-form" name="create-account-form" class="sliding-form" action="/Ocena/addUser" method="post">
                        <fieldset class="step">
                            <legend>Person details </legend>
                            <p>
                                <label for="first-name">First name </label>
                                <input id="first-name" type="text" name="first-name" required="true"/>

                            </p>
                            <p>
                                <label for="last-name">Last name </label>
                                <input id="last-name" name="last-name" type="text" required="true"/>
                            </p>
                            <p>
                                <label for="reference-number">Reg/staff number </label>
                                <input id="reference-number" name="reference-number" type="text"  required="true"/>
                            </p>
                            <p>
                                <label for="national-id-or-passport">National id/passport</label>
                                <input id="national-id-or-passport" name="national-id-or-passport" type="text"  required="true"/>
                            </p>
                        </fieldset>
                        <fieldset class="step">
                            <legend>Phone and email contact </legend>
                            <p>
                                <label for="mobile-number">Mobile number </label>
                                <input id="mobile-number" name="mobile-number" type="text"  required="true"/>
                            </p>
                            <p>
                                <label for="fixed-number">Fixed number </label>
                                <input id="fixed-number" name="fixed-number" type="text" />
                            </p>
                            <br>
                            <p>
                                <label for="email-address">Email address </label>
                                <input id="email-address" name="email-address" placeholder="student@uni.ac.ke" type="email" required="true" />
                            </p>
                        </fieldset>
                        <fieldset class="step">
                            <legend>Postal contact </legend>
                            <p>
                                <label for="box-number">Box number </label>
                                <input id="box-number" name="box-number" type="text" />
                            </p>
                            <p>
                                <label for="postal-code">Postal code</label>
                                <input id="postal-code" name="postal-code" type="text" />
                            </p>
                            <p>
                                <label for="town">Town </label>
                                <input id="town" name="town" type="text" />
                            </p>
                            <p>
                                <label for="country">Country</label>
                                <select id="country" name="country">
                                    <c:forEach var="country" items="${applicationScope.countries}">
                                        <option value="${country.id}">${country.name}</option>
                                    </c:forEach>
                                </select>
                            </p>

                        </fieldset>
                        <fieldset class="step">
                            <legend>Campus details </legend>
                            <p>
                                <label for="campus-college">College </label>
                                <select name="campus-college" id="campus-college" onchange="updateFaculties();
                                        return false;" required="true">
                                    <c:forEach var="college" items="${sessionScope.colleges}">
                                        <option value="${college.id}" selected> ${college.name} </option>
                                    </c:forEach>
                                </select>
                            </p>
                            <p id="campus-faculty-holder">

                            </p>
                            <p id="campus-department-holder">

                            </p>
                            <p>
                                <label for="faculty-member-role">Faculty member role </label>
                                <select name="faculty-member-role" id="faculty-member-role" onchange="displayAdmissionYearHolder();
                                        return false;" required="true">
                                    <c:forEach var="facultyMemberRole" items="${sessionScope.facultyMemberRoles}">
                                        <option value="${facultyMemberRole.id}" selected> ${facultyMemberRole.facultyMemberRole} </option>
                                    </c:forEach>
                                </select>
                            </p>
                            <p id="admission-year-holder">
                                <label for="admission-year">Admission month & year</label>
                                <input type="date" name="admission-year" id="admission-year" />
                            </p>
                        </fieldset>
                        <fieldset class="step">
                            <legend>Confirm</legend>
                            <p>
                                Kindly confirm the submission of your registration details.
                            </p>
                            <p class="central">
                                <button id="register-button" type="submit" class="btn btn-default slider-submit-button">Register</button>
                            </p>
                            <legend>Abort</legend>
                            <p>
                                Click the button below to abort this mission
                            </p>
                            <p class="central">
                                <button id="abort-button" type="button" class="btn btn-default" onclick="loadWindow('/Ocena/home')">Abort</button>
                            </p>
                        </fieldset>
                    </form>
                </div>

                <div id="slider-navigation" style="display:none;">
                    <ul>
                        <li class="selected">
                            <a href="#">Person</a>
                        </li>
                        <li>
                            <a href="#">Phone/email</a>
                        </li>
                        <li>
                            <a href="#">Postal</a>
                        </li>
                        <li>
                            <a href="#">Campus</a>
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