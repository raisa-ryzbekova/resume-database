<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
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
            <c:set var="section" value="${resume.getSection(s_type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <c:choose>
                <c:when test="${s_type == 'OBJECTIVE' || s_type == 'PERSONAL'}">
                    <dl>
                        <dt>${s_type.title}</dt>
                        <dd><input type="text" name="${s_type.name()}" size=30 value='<%=section%>'></dd>
                    </dl>
                </c:when>
                <c:when test="${s_type == 'ACHIEVEMENT' || s_type == 'QUALIFICATIONS'}">
                    <jsp:useBean id="s_type" type="ru.javawebinar.basejava.model.SectionType"/>
                    <dl>
                        <dt>${s_type.title}</dt>
                        <dd><textarea name="${s_type.name()}" rows="5"
                                      cols="35"><%=section == null ? "" : resume.getSection(s_type)%></textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${s_type == 'EXPERIENCE' || s_type == 'EDUCATION'}">
                    <dl>
                        <dt></br>${s_type.title}</dt>
                        </dt>
                    </dl>
                    <c:forEach var="company" items="<%=((CompanySection) section).getSectionContent()%>"
                               varStatus="counter">
                        </br>
                        <dl>
                            <dt>Название компании</dt>
                            <dd><input type="text" name="${s_type}" size=30 value="${company.companyName}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт компании</dt>
                            <dd><input type="text" name="${s_type}url" size=30 value="${company.url}"></dd>
                        </dl>
                        <c:forEach var="position" items="${company.listOfPositions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Company.PositionInCompany"/>
                            <dl>
                                <dt>Начальная дата:</dt>
                                <dd><input type="text" name="${s_type}${counter.index}startDate" size=30
                                           value="<%=position.getEndDate()%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Конечная дата:</dt>
                                <dd><input type="text" name="${s_type}${counter.index}endDate" size=30
                                           value="<%=position.getStartDate()%>" placeholder="MM/yyyy">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='${s_type}${counter.index}position' size=30
                                           value="${position.position}"></dd>
                            </dl>
                            <dl>
                                <dt>Обязанности:</dt>
                                <dd><textarea name="${s_type}${counter.index}function" rows=5
                                              cols=35>${position.functions}</textarea></dd>
                            </dl>
                        </c:forEach>
                    </c:forEach>
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