<%-- 
    Document   : facultyHome
    Created on : Nov 18, 2015, 12:28:07 PM
    Author     : siech
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="ocena" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ocena:faculty-admin>
    <jsp:attribute name="title"> Ocena - faculty home </jsp:attribute>
    <jsp:attribute name="content">

        <div class="container">
            <div id="content">
                <h1>${sessionScope.faculty.name} dashboard</h1>
                <div>
                    <button class="pull-left btn btn-default" onclick="loadWindow('/Ocena/viewEvaluationSessions?facultyId=${sessionScope.faculty.id}&departmentId=${sessionScope.department.id}')">View evaluation sessions</button>
                </div>
                <div id="accordion">
                    <h1>Rating values</h1>
                    <div>
                        <table id="rating-value-table" class="table table-responsive table-hover parent-table">
                            <thead>
                                <tr>
                                    <th>&nbsp;</th>
                                    <th colspan="2"> Rating value </th>
                                    <th>&nbsp;</th>
                                    <th>&nbsp;</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="5"> Rating values </td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="aRatingType" items="${sessionScope.ratingTypeAndValuesMap.keySet()}">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td colspan="4"> <strong> ${aRatingType.ratingType} rating </strong> </td>
                                    </tr>
                                    <c:forEach var="ratingValue" items="${ratingTypeAndValuesMap.get(aRatingType)}" varStatus="pos">
                                        <tr>
                                            <td> &nbsp; </td>
                                            <td> ${pos.count} </td>
                                            <td> ${ratingValue.rating} </td>
                                            <td><button onclick="editRatingValue('${ratingValue.id}', '${ratingValue.rating}', '${aRatingType.id}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                            <td><button onclick="removeRatingValue('${ratingValue.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="btn btn-default" onclick="addRatingValue();
                                    return false;"> Add </button>
                        </div>
                    </div>
                    <h1> Question categories </h1>
                    <div>
                        <table id="question-category-table" class="table table-responsive table-hover parent-table">
                            <thead>
                                <tr>
                                    <th>&nbsp;</th>
                                    <th> Question category </th>
                                    <th>&nbsp;</th>
                                    <th>&nbsp;</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="4"></td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="category" items="${sessionScope.questionCategories}" varStatus="pos">
                                    <tr <c:if test="pos % 2 == 0"> class="even" </c:if>>
                                        <td> ${pos.count} </td>
                                        <td> ${category.category} </td>
                                        <td><button onclick="editQuestionCategory('${category.id}', '${category.category}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                        <td><button onclick="removeQuestionCategory('${category.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="btn btn-default" onclick="addQuestionCategory();
                                    return false;"> Add </button>
                        </div>
                    </div>
                    <h1> Evaluation questions</h1>
                    <div>
                        <table id="evaluation-question-table" class="table table-responsive table-hover parent-table">
                            <thead>
                                <tr>
                                    <th>&nbsp;</th>
                                    <th>&nbsp; </th>
                                    <th> Evaluation question </th>
                                    <th> Means of answering </th>
                                    <th> Rating type </th>
                                    <th> &nbsp; </th>
                                    <th> &nbsp; </th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="7"> Evaluation questions </td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="questionCategory" items="${sessionScope.questionsInQuestionCategoryMap.keySet()}">
                                    <tr>
                                        <td> &nbsp; </td>
                                        <td colspan="6"> <strong> ${questionCategory.category} </strong> </td>
                                    </tr>
                                    <c:forEach var="question" items="${questionsInQuestionCategoryMap.get(questionCategory)}" varStatus="pos">
                                        <tr>
                                            <td> &nbsp; </td>
                                            <td> ${pos.count} </td>
                                            <td> ${question.question} </td>
                                            <td> ${sessionScope.meansOfAnsweringByQuestionMap.get(question).meansOfAnswering} </td>
                                            <td> ${sessionScope.ratingTypesByQuestionMap.get(question).ratingType} </td>
                                            <td><button onclick="editQuestion('${question.question}', '${question.id}', '${questionCategory.id}', '${sessionScope.meansOfAnsweringByQuestionMap.get(question).id}', '${sessionScope.ratingTypesByQuestionMap.get(question).id}', '${sessionScope.faculty.id}', '')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                            <td><button onclick="removeQuestion('${question.id}', '${sessionScope.faculty.id}', '')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="btn btn-default" onclick="addQue
                                    stion('${sessionScope.faculty.id}', 'faculty');
                                    return false;"> Add </button>
                        </div>
                    </div>
                    <h1>Admissions</h1>
                    <div>
                        <table id="admission-table" class="table table-responsive table-hover parent-table">
                            <thead>
                                <tr>
                                    <th>&nbsp;</th>
                                    <th> Admission </th>
                                    <th>&nbsp;</th>
                                    <th>&nbsp;</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="4"> Admission </td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="admission" items="${sessionScope.admissions}" varStatus="pos">
                                    <tr>
                                        <td> ${pos.count} </td>
                                        <td> ${admission.admission} </td>
                                        <td><button onclick="editAdmission('${admission.id}', '${admission.admission}')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                        <td><button onclick="removeAdmission('${admission.id}')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="btn btn-default" onclick="addAdmission();
                                    return false;"> Add </button>
                        </div>
                    </div>
                    <h1> Degrees </h1>
                    <div>
                        <table id="degree-table" class="table table-responsive table-hover parent-table">
                            <thead>
                                <tr>
                                    <th>&nbsp;</th>
                                    <th>&nbsp; </th>
                                    <th> Degree </th>
                                    <th> &nbsp; </th>
                                    <th> &nbsp; </th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="5"> Degree </td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="admission" items="${sessionScope.degreesByAdmissionMap.keySet()}">
                                    <tr>
                                        <td> &nbsp; </td>
                                        <td colspan="4"> <strong> ${admission.admission} </strong> </td>
                                    </tr>
                                    <c:forEach var="degree" items="${degreesByAdmissionMap.get(admission)}" varStatus="pos">
                                        <tr>
                                            <td onclick="loadWindow('/Ocena/retrieveCoursesAtHome?degreeId=${degree.id}')"> &nbsp; </td>
                                            <td onclick="loadWindow('/Ocena/retrieveCoursesAtHome?degreeId=${degree.id}')"> ${pos.count} </td>
                                            <td onclick="loadWindow('/Ocena/retrieveCoursesAtHome?degreeId=${degree.id}')"> ${degree.name} </td>
                                            <td><button onclick="editDegree('${degree.name}', '${degree.id}', '${admission.id}', '${sessionScope.faculty.id}', '')"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                            <td><button onclick="removeDegree('${degree.id}', '${sessionScope.faculty.id}', '')"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>
                                        </tr>
                                    </c:forEach>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="btn btn-default" onclick="addD
                                    egree('${sessionScope.faculty.id}', 'faculty');
                                    return false;"> Add </button>
                        </div>
                    </div>
                </div>
                <div class="dialog" id="rating-value-dialog">
                    <table>
                        <tr>
                            <td> Rating value </td>
                            <td> <input type="text" id="rating-value" placeholder="50 - 74 %"></td>
                        </tr>
                        <tr>
                            <td> Rating type </td>
                            <td> 
                                <select id="rating-type">
                                    <c:forEach var="aRatingType" items="${sessionScope.ratingTypeAndValuesMap.keySet()}">
                                        <option value="${aRatingType.id}"> ${aRatingType.ratingType} </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="dialog" id="question-category-dialog">
                    <table>
                        <tr>
                            <td> Question category </td>
                            <td> <input type="text" id="question-category"></td>
                        </tr>
                    </table>
                </div>
                <div class="dialog" id="evaluation-question-dialog">
                    <table>
                        <tr>
                            <td> Evaluation question </td>
                            <td> <input type="text" id="evaluation-question" required="true"></td>
                        </tr>
                        <tr>
                            <td> Question category </td>
                            <td>
                                <select id="evaluation-question-category">
                                    <c:forEach var="questionCategory" items="${sessionScope.questionCategories}">
                                        <option value="${questionCategory.id}"> ${questionCategory.category} </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr> 
                        <tr>
                            <td> Means of answering </td>
                            <td> 
                                <select id="evaluation-question-means-of-answering" onchange="checkMeansOfAnswering()">
                                    <c:forEach var="means" items="${sessionScope.meansOfAnsweringList}">
                                        <option value="${means.id}"> ${means.meansOfAnswering} </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr id="rating-type-hidden">
                            <td> Rating type </td>
                            <td> 
                                <select id="evaluation-question-rating-type">
                                    <c:forEach var="aRatingType" items="${sessionScope.ratingTypeAndValuesMap.keySet()}">
                                        <option value="${aRatingType.id}"> ${aRatingType.ratingType} </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="dialog" id="admission-dialog">
                    <table>
                        <tr>
                            <td> Admission </td>
                            <td> <input type="text" id="admission"></td>
                        </tr>
                    </table>
                </div>
                <div class="dialog" id="degree-dialog">
                    <table>
                        <tr>
                            <td> Degree </td>
                            <td> <input type="text" id="degree-name" required="true"></td>
                        </tr>
                        <tr>
                            <td> Admission </td>
                            <td>
                                <select id="degree-admission">
                                    <c:forEach var="admission" items="${sessionScope.admissions}">
                                        <option value="${admission.id}"> ${admission.admission} </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr> 
                    </table>
                </div>
                <div class="dialog" id="message-dialog">
                    <p id="message"></p>
                </div>
            </div>    
        </div>

    </jsp:attribute>
</ocena:faculty-admin>

