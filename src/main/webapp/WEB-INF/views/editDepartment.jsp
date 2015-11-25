<%-- 
    Document   : editDepartment
    Created on : Jun 16, 2015, 9:20:02 AM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:system-admin>
    <jsp:attribute name="title"> Ocena - edit department </jsp:attribute>
    <jsp:attribute name="content">

        <div id="slider-content">
            <div id="slider-wrapper">
                <h1>Edit details for ${sessionScope.department.name}</h1>
                <div id="steps">
                    <form id="edit-department-form" name="edit-department-form" class="sliding-form" action="/Ocena/saveEditedDepartment" method="post">
                        <fieldset class="step">
                            <legend>Department details </legend>
                            <p>
                                <label for="department-name"> Department name </label>
                                <input id="department-name" type="text" name="department-name" value="${sessionScope.department.name}" required="true" />
                            </p>
                            <p>
                                <label for="department-abbreviation"> Department abbreviation </label>
                                <input id="department-abbreviation" name="department-abbreviation" value="${sessionScope.department.abbreviation}" type="text" required="true" />
                            </p>
                            <input id="department-id" name="departmentId" type="hidden" value="${sessionScope.department.id}"  />
                            <input id="faculty-id" name="facultyId" type="hidden" value="${sessionScope.faculty.id}" />
                            <input id="contact-id" name="contact-id" type="hidden" value="${sessionScope.contact.id}" />
                        </fieldset>
                        <fieldset class="step">
                            <legend>Phone Contact </legend>
                            <p>
                                <label for="mobile-number">Mobile number </label>
                                <input id="mobile-number" name="mobile-number" value="${sessionScope.phoneContact.mobileNumber}" type="text" required="true" />
                            </p>
                            <p>
                                <label for="fixed-number">Fixed number </label>
                                <input id="fixed-number" name="fixed-number" value="${sessionScope.phoneContact.fixedNumber}" type="text"  />
                            </p>
                            <input type="hidden" name="phone-contact-id" value="${sessionScope.phoneContact.id}">
                        </fieldset>
                        <fieldset class="step">
                            <legend>Email Contact </legend>
                            <p>
                                <label for="email-address">Email address </label>
                                <input id="email-address" name="email-address" placeholder="sci@uni.ac.ke" value="${sessionScope.emailContact.emailAddress}" type="text" required="true" />
                            </p>
                            <input type="hidden" name="email-contact-id" value="${sessionScope.emailContact.id}">
                        </fieldset>
                        <fieldset class="step">
                            <legend>Postal Contact </legend>
                            <p>
                                <label for="box-number">Box number </label>
                                <input id="box-number" name="box-number" value="${sessionScope.postalContact.boxNumber}" type="text" />
                            </p>
                            <p>
                                <label for="postal-code">Postal code</label>
                                <input id="postal-code" name="postal-code" value="${sessionScope.postalContact.postalCode}" type="text" />
                            </p>
                            <p>
                                <label for="town">Town </label>
                                <input id="town" name="town" value="${sessionScope.postalContact.town}" type="text" />
                            </p>
                            <p>
                                <label for="country">Country</label>
                                <select id="country" name="country" value="${sessionScope.postalContact.country}">
                                    <c:forEach var="country" items="${applicationScope.countries}">
                                        <option value="${country.id}">${country.name}</option>
                                    </c:forEach>
                                </select>
                            </p>
                            <input type="hidden" name="postal-contact-id" value="${sessionScope.postalContact.id}">
                        </fieldset>
                        <fieldset class="step">
                            <legend>Confirm</legend>
                            <p>
                                Kindly confirm the submission of the department details.
                            </p>
                            <p class="central">
                                <button id="register-button" type="submit" class="btn btn-default slider-submit-button"> Submit </button>
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
                            <a href="#">Person</a>
                        </li>
                        <li>
                            <a href="#">Phone</a>
                        </li>
                        <li>
                            <a href="#">Email</a>
                        </li>
                        <li>
                            <a href="#">Postal</a>
                        </li>
                        <li>
                            <a href="#">Confirm</a>
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
