<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
        <title>Резюме ${resume.fullName}</title>
    </head>
    <body>
        <jsp:include page="fragments/header.jsp"/>
            <section>
                <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="uuid" value="${resume.uuid}">
                        <dl>
                            <dt>Имя</dt>
                            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
                        </dl>
                    <h3>Контакты:</h3>
                        <c:forEach var="c_type" items="<%=ContactType.values()%>">
                            <dl>
                                <dt>${c_type.title}</dt>
                                <dd><input type="text" name="${c_type.name()}" size=30 value="${resume.getContact(c_type)}"></dd>
                            </dl>
                        </c:forEach>
                    <h3>Секции:</h3>
                        <c:forEach var="s_type" items="<%=SectionType.values()%>">
                            <c:choose>
                                <c:when test="${s_type == 'OBJECTIVE' || s_type == 'PERSONAL'}">
                                    <dl>
                                        <dt>${s_type.title}</dt>
                                        <dd><input type="text" name="${s_type.name()}" size=30 value="${resume.getSection(s_type)}"></dd>
                                    </dl>
                                </c:when>
                                <c:when test="${s_type == 'ACHIEVEMENT' || s_type == 'QUALIFICATIONS'}">
                                    <jsp:useBean id="s_type" type="ru.javawebinar.basejava.model.SectionType"/>
                                    <dl>
                                        <dt>${s_type.title}</dt>
                                        <dd><textarea name="${s_type.name()}" rows="5" cols="35"><%=resume.getSection(s_type)%></textarea></dd>
                                    </dl>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <hr>
                        <button type="submit">Сохранить</button>
                        <button onclick="window.history.back()">Отменить</button>
                </form>
            </section>
        <jsp:include page="fragments/footer.jsp"/>
    </body>
</html>