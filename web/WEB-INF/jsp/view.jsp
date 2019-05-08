<%@ page import="ru.javawebinar.basejava.model.Sections" %>
<%@ page import="ru.javawebinar.basejava.web.ResumeServlet" %>
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
        <h2>${resume.fullName}&nbsp;<a href="resumes?action=edit&uuid=${resume.uuid}"><img src="/img/pencil.png"></a></h2>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.Contacts, java.lang.String>" />
                ${contactEntry.key.title} : <%=ResumeServlet.toHTML(contactEntry.getKey(), contactEntry.getValue())%><br>
            </c:forEach>
        </p>
        <p>
            <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javawebinar.basejava.model.Sections, ru.javawebinar.basejava.model.AbstractResumeSection>" />
                <%=ResumeServlet.toViewHTML(sectionEntry.getKey(), sectionEntry.getValue())%>
            </c:forEach>
        </p>
    </section>
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>
