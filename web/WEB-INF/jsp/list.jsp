<%@ page import="ru.javawebinar.basejava.model.Contacts" %>
<%@ page import="ru.javawebinar.basejava.util.HTMLUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/header.jsp" />
    <section>
        <a href="resumes?action=create">Новое резюме</a>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Имя</th>
                <th>eMail</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="resume" items="${resumes}">
                <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" />
                <tr>
                    <td><a href="resumes?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td><%=HTMLUtil.contactToHTML(Contacts.EMAIL, resume.getContact(Contacts.EMAIL))%></td>
                    <td><a href="resumes?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" title="удалить резюме"></a></td>
                    <td><a href="resumes?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" title="редактировать резюме"></a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</body>
</html>
