<%@ page import="ru.javawebinar.basejava.model.LifePeriod" %>
<%@ page import="ru.javawebinar.basejava.model.Sections" %>
<%@ page import="ru.javawebinar.basejava.util.htmlUtil" %>
<%@ page import="java.util.Comparator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request" />
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/header.jsp" />
    <section>
        <h2>${resume.fullName}&nbsp;<a href="resumes?action=edit&uuid=${resume.uuid}"><img src="/resumes-webapp/img/pencil.png"></a></h2>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.Contacts, java.lang.String>" />
                ${contactEntry.key.title} : <%=htmlUtil.contactToHTML(contactEntry.getKey(), contactEntry.getValue())%><br>
            </c:forEach>
        </p>
        <p>
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.Sections, ru.javawebinar.basejava.model.AbstractResumeSection>" />
                <h3>${sectionEntry.key.title}</h3>
                <c:choose>
                    <c:when test="${sectionEntry.key==Sections.OBJECTIVE || sectionEntry.key==Sections.PERSONAL}">
                        <p>${sectionEntry.value.data}</p>
                    </c:when>
                    <c:when test="${sectionEntry.key==Sections.ACHIEVEMENTS || sectionEntry.key==Sections.QUALIFICATIONS}">
                        <ul>
                            <c:forEach var="string" items="${sectionEntry.value.data}">
                                <li>${string}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${sectionEntry.key==Sections.EXPERIENCE || sectionEntry.key==Sections.EDUCATION}">
                        <c:forEach var="lifeStage" items="${sectionEntry.value.data}">
                            <jsp:useBean id="lifeStage" type="ru.javawebinar.basejava.model.LifeStage" />
                            <c:set var="organization" value="${lifeStage.organization}" />
                            <a href="${organization.webLink}"><h4>${organization.title}</h4></a>
                            <c:set var="lifePeriods" value="${lifeStage.data}" />
                            <jsp:useBean id="lifePeriods" type="java.util.List<ru.javawebinar.basejava.model.LifePeriod>" />
<%--                            <% lifePeriods.sort(Comparator.comparing(LifePeriod::getStartDate).reversed()); %> --%>
                            <table>
                                <c:forEach var="lifePeriod" items="${lifePeriods}">
                                    <jsp:useBean id="lifePeriod" type="ru.javawebinar.basejava.model.LifePeriod" />
                                    <tr>
                                        <td width="150px" valign="top">
                                            <%=htmlUtil.briefDate(lifePeriod.getStartDate())%> - <%=htmlUtil.briefDate(lifePeriod.getEndDate())%>
                                        </td>
                                        <td>
                                            <b>${lifePeriod.title}</b><br>
                                            ${lifePeriod.description}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        </p>
    </section>
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>
