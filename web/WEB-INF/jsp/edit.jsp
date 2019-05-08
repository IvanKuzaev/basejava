<%@ page import="ru.javawebinar.basejava.model.Contacts" %>
<%@ page import="ru.javawebinar.basejava.model.Sections" %>
<%@ page import="ru.javawebinar.basejava.web.ResumeServlet" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resumes?action=edit&uuid=${resume.fullName}">edit</a></h2>
    <form name="editForm" method="post" action="resumes" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты</h3>
        <p>
            <c:forEach var="contactType" items="<%=Contacts.values()%>">
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType.name()}" size="30" value="${resume.getContact(contactType)}"></dd>
                </dl>
            </c:forEach>
        </p>
        <p>
            <c:forEach var="sectionType" items="<%=Sections.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.Sections"/>
                <%=ResumeServlet.toEditHTML(sectionType, resume.getSection(sectionType))%>
            </c:forEach>
        </p>
        <hr>
        <button type="submit" onclick="selectAll()">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>

