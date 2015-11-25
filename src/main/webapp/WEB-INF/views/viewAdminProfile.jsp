<%-- 
    Document   : viewUserProfile
    Created on : Jul 17, 2015, 7:42:53 PM
    Author     : Ben Siech
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:faculty-admin>
    <jsp:attribute name="title"> Ocena - admin profile </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1>Your profile</h1>

                <div>
                    <form id="edit-admin-user-profile-form" action="/Ocena/editUserProfile" method="POST"> 
                        <input name="person-id" id="edit-admin-person-id" type="hidden" value="${sessionScope.person.id}">
                        <input name="faculty-id" type="hidden" value="${sessionScope.faculty.id}"/>
                        <input name="college-id" type="hidden" value="${sessionScope.college.id}"/>
                        <input name="contact-id" type="hidden" value="${sessionScope.contact.id}"/>
                        <input name="phone-contact-id" type="hidden" value="${sessionScope.phoneContact.id}">
                        <input name="email-contact-id" type="hidden" value="${sessionScope.emailContact.id}">
                        <input name="postal-contact-id" type="hidden" value="${sessionScope.postalContact.id}">
                        <input name="faculty-member-id" type="hidden" value="${sessionScope.facultyMember.id}">
                        <table id="edit-admin-user-profile-table">
                            <tr>
                                <th>Person details </th>
                            </tr>
                            <tr>
                                <td><label for="first-name">First name </label></td>
                                <td><input id="edit-admin-first-name" type="text" name="first-name" required="true" value="${sessionScope.person.firstName}"/></td>
                                <td><label for="last-name">Last name </label></td>
                                <td><input id="edit-admin-last-name" name="last-name" type="text" required="true" value="${sessionScope.person.lastName}"/></td>
                            </tr>
                            <tr>
                                <td><label for="reference-number">Reg/staff number </label></td>
                                <td><input id="edit-admin-reference-number" name="reference-number" type="text"  required="true" value="${sessionScope.person.referenceNumber}"/></td>
                                <td><label for="national-id-or-passport">National id/passport</label></td>
                                <td><input id="edit-admin-national-id-or-passport" name="national-id-or-passport" type="text"  required="true" value="${sessionScope.person.nationalIdOrPassport}"/></td>
                            </tr>
                            <tr><td colspan="4"></td></tr>
                            <tr>
                                <th>Phone and email contact</th>
                            </tr>
                            <tr>
                                <td><label for="mobile-number">Mobile number </label></td>
                                <td><input id="edit-admin-mobile-number" name="mobile-number" type="text"  required="true" value="${sessionScope.phoneContact.mobileNumber}"/></td>
                                <td><label for="fixed-number">Fixed number </label></td>
                                <td><input id="edit-admin-fixed-number" name="fixed-number" type="text" value="${sessionScope.phoneContact.fixedNumber}"/></td>
                            </tr>
                            <tr>
                                <td><label for="email-address">Email address </label>
                                <td><input id="edit-admin-email-address" name="email-address" placeholder="student@uni.ac.ke" type="email" required="true" value="${sessionScope.emailContact.emailAddress}"/>
                            </tr>
                            <tr><td colspan="4"></td></tr>
                            <tr>
                                <th>Postal contact </th>
                            </tr> 
                            <tr>
                                <td><label for="box-number">Box number </label></td>
                                <td><input id="edit-admin-box-number" name="box-number" type="text" value="${sessionScope.postalContact.boxNumber}"/></td>
                                <td><label for="postal-code">Postal code</label></td>
                                <td><input id="edit-admin-postal-code" name="postal-code" type="text" value="${sessionScope.postalContact.postalCode}"/></td>
                            </tr>
                            <tr>
                                <td><label for="town">Town </label></td>
                                <td><input id="edit-admin-town" name="town" type="text" value="${sessionScope.postalContact.town}"/></td>
                                <td><label for="country">Country</label></td>
                                <td>
                                    <select id="edit-admin-country" name="country" value="${sessionScope.postalContact.country.id}">
                                        <c:forEach var="country" items="${applicationScope.countries}">
                                            <option value="${country.id}" <c:if test="${country.id} = ${sessionScope.postalContact.country.id}">selected</c:if>>${country.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr><td colspan="4"></td></tr>
                            <tr>    
                                <th>Campus details </th>
                            <tr>
                                <td><label for="campus-college">College </label></td>
                                <td>
                                    <select id="edit-admin-campus-college" name="campus-college" onchange="updateAdminFaculties();
                                            return false;" required="true">
                                        <c:forEach var="college" items="${sessionScope.colleges}">
                                            <option value="${college.id}"  <c:if test="${country.id} = ${sessionScope.postalContact.country.id}">selected</c:if>> ${college.name} </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr id="edit-admin-campus-faculty-department-holder">

                            </tr>
                            <tr><td colspan="4"></td></tr>
                            <tr>
                                <th>Password </th>
                            </tr> 
                            <tr>
                                <td><label for="old-password">Current password </label></td>
                                <td><input id="edit-admin-old-password" name="old-password" type="password" onchange="validatePassword()"/></td>
                                <td id="admin-valid-password-information"></td>
                            </tr>
                            <tr>
                                <td><label for="new-password">New password </label></td>
                                <td><input id="edit-admin-new-password" name="new-password" type="password"/></td>
                            </tr>
                            <tr>
                                <td><label for="confirm-password">Confirm password </label></td>
                                <td><input id="edit-admin-confirm-password" name="confirm-password" type="password" onchange="matchPassword()"/></td>
                                <td id="admin-matching-password-information"></td>
                            </tr>

                            <tr><td colspan="4"></td></tr>
                        </table>
                        <div>
                            <table>
                                <tr>
                                    <td><label>Click to edit</label></td>
                                    <td><input type="submit" class="btn btn-default" value="Edit your profile"></td>
                                </tr>
                            </table>
                        </div>
                    </form>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>
        </div>

    </jsp:attribute>
</ocena:faculty-admin>
