<%-- 
    Document   : editFaculty
    Created on : Jun 13, 2015, 4:46:00 PM
    Author     : Ben Siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:slidingform>
    <jsp:attribute name="title"> Ocena - edit faculty </jsp:attribute>
    <jsp:attribute name="content">

        <h1>Edit details for ${sessionScope.faculty.name}</h1>

        <div id="steps">
            <form id="edit-faculty-form" name="edit-faculty-form" action="/Ocena/saveEditedFaculty" method="post">
                <fieldset class="step">
                    <legend>Faculty details </legend>
                    <p>
                        <label for="faculty-name"> Faculty name </label>
                        <input id="faculty-name" type="text" name="faculty-name" value="${sessionScope.faculty.name}" />
                    </p>
                    <p>
                        <label for="faculty-abbreviation"> Faculty abbreviation </label>
                        <input id="faculty-abbreviation" name="faculty-abbreviation" value="${sessionScope.faculty.abbreviation}" type="text" />
                    </p>
                    <input id="faculty-id" name="facultyId" type="hidden" value="${sessionScope.faculty.id}"  />
                    <input id="college-id" name="collegeId" type="hidden" value="${sessionScope.college.id}" />
                    <input id="contact-id" name="contact-id" type="hidden" value="${sessionScope.contact.id}" />
                </fieldset>
                <fieldset class="step">
                    <legend>Phone Contact </legend>
                    <p>
                        <label for="mobile-number">Mobile number </label>
                        <input id="mobile-number" name="mobile-number" value="${sessionScope.phoneContact.mobileNumber}" type="text"  />
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
                        Kindly confirm the submission of the faculty contact details.
                    </p>
                    <p class="submit">
                        <button id="register-button" type="submit" class="btn btn-default">Register</button>
                    </p>
                </fieldset>
            </form>
        </div>

        <div id="navigation" style="display:none;">
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
    </jsp:attribute>
</ocena:slidingform>