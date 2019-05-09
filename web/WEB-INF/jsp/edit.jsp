<%@ page import="ru.javawebinar.basejava.model.Contacts" %>
<%@ page import="ru.javawebinar.basejava.model.LifePeriod" %>
<%@ page import="ru.javawebinar.basejava.model.Sections" %>
<%@ page import="ru.javawebinar.basejava.web.ResumeServlet" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/edit.js"></script>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request" />
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
<section>
    <h2>${resume.fullName}</h2>
    <form name="editForm" method="post" action="resumes" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="action" value="${action}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты</h3>
        <p>
            <c:forEach var="contactType" items="${Contacts.values()}">
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType.name()}" size="30" value="${resume.getContact(contactType)}"></dd>
                </dl>
            </c:forEach>
        </p>
        <p>
            <c:forEach var="sectionType" items="${Sections.values()}">
                <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.Sections" />
                <br><h3 style="display:inline-block">${sectionType.title}</h3>
                <c:set var="name" value="${sectionType.name()}" />
                <c:set var="resumeSection" value="${resume.getSection(sectionType)}" />
                <c:if test="${resumeSection != null}">
                    <jsp:useBean id="resumeSection" type="ru.javawebinar.basejava.model.AbstractResumeSection" />
                </c:if>
                <c:choose>
                    <c:when test="${sectionType==Sections.OBJECTIVE || sectionType==Sections.PERSONAL}">
                        <br>
                        <input type="text" name="${name}" value="${resumeSection.data}">
                    </c:when>
                    <c:when test="${sectionType==Sections.ACHIEVEMENTS || sectionType==Sections.QUALIFICATIONS}">
                        <br>
                        <input type="text" id="item${name}">
                        <img src="/resumes-webapp/img/add.png" title="добавить строку" onclick="addItem(${name}, 'item${name}')">
                        <img src="/resumes-webapp/img/delete.png" title="удалить строку" onclick="removeItem(${name})"><br>
                        <select name="${name}" size="5">
                            <c:forEach var="string" items="${resumeSection.data}">
                                <option value="${string}">${string}</option>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:when test="${sectionType==Sections.EXPERIENCE || sectionType==Sections.EDUCATION}">
                        <img src="/resumes-webapp/img/add.png" title="добавить организацию" onclick="addOrganization('${name}')"><br>
                        <section id="section${name}">
                            <c:set var="count1" value="0" />
                            <c:forEach var="lifeStage" items="${resumeSection.data}">
                                <jsp:useBean id="lifeStage" type="ru.javawebinar.basejava.model.LifeStage" />
                                <c:set var="count1" value="${count1 + 1}" />
                                <c:set var="organization" value="${lifeStage.organization}" />
                                <fieldset id="organization${name}${count1}">
                                    <legend>
                                        <img src="/resumes-webapp/img/delete.png" title="удалить организацию" onclick="removeOrganization('organization${name}${count1}')">
                                        <label>Организация <input type="text" value="${organization.title}" required></label>
                                        <label>Сайт <input type="text" value="${organization.webLink}"></label>
                                        <button type="button" onclick="addPosition('table${name}${count1}')">Новая позиция</button>
                                    </legend>
                                    <table id="table${name}${count1}">
                                        <c:set var="lifePeriods" value="${lifeStage.data}" />
                                        <jsp:useBean id="lifePeriods" type="java.util.List<ru.javawebinar.basejava.model.LifePeriod>" />
                                        <% lifePeriods.sort(Comparator.comparing(LifePeriod::getStartDate).reversed()); %>
                                        <c:set var="count2" value="0" />
                                        <c:forEach var="lifePeriod" items="${lifePeriods}">
                                            <c:set var="count2" value="${count2 + 1}" />
                                            <jsp:useBean id="lifePeriod" type="ru.javawebinar.basejava.model.LifePeriod" />
                                            <tr id="tr${name}_${count1}_${count2}">
                                                <td width="350px" valign="top">
                                                    <label>с <input type="date" value="${lifePeriod.startDate}" required></label>
                                                    <label>по <input type="date" value="${lifePeriod.endDate}" required></label>
                                                </td>
                                                <td>
                                                    <label>позиция <input type="text" value="${lifePeriod.title}" required></label><br>
                                                    <label>описание <input type="text" value="${lifePeriod.description}"></label>
                                                </td>
                                                <td valign="middle" align="left">
                                                    <img src="/resumes-webapp/img/delete.png" title="удалить позицию" onclick="removePosition('tr${name}_${count1}_${count2}')">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </fieldset>
                            </c:forEach>
                        </section>
                    </c:when>
                </c:choose>
        </c:forEach>
        </p>
        <hr>
        <button type="submit" onclick="onSubmit()">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>

