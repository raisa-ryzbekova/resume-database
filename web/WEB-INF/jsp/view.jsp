<%@ page import="ru.javawebinar.basejava.model.CompanySection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
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
                <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
                <c:forEach var="contactEntry" items="${resume.contacts}">
                    <jsp:useBean id="contactEntry"
                                 type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                    <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
                </c:forEach>
                <hr>
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry"
                                 type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                                 ru.javawebinar.basejava.model.Section>"/>
                    <h1><%=sectionEntry.getKey().getTitle()%><br/></h1>
                    <c:set var="sectionType" value="${sectionEntry.key}"/>
                    <c:set var="section" value="${sectionEntry.value}"/>
                    <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
                    <c:choose>
                        <c:when test="${sectionType == 'OBJECTIVE'}">
                            <h3><%=sectionEntry.getValue()%></h3>
                        </c:when>
                        <c:when test="${sectionType == 'PERSONAL'}">
                            <%=sectionEntry.getValue()%><br/>
                        </c:when>
                        <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                            <ul>
                                <c:forEach var="listSectionItem" items="<%=((ListSection) section).getSectionContent()%>">
                                    <li>${listSectionItem}</li></br>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                            <c:forEach var="companySectionItem" items="<%=((CompanySection) section).getSectionContent()%>">
                            <h3><a href=${companySectionItem.url}>${companySectionItem.companyName}</a></h3>
                                <c:forEach var="position" items="${companySectionItem.listOfPositions}">
                                    <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Company.PositionInCompany"/>
                                    <table cellpadding="2">
                                        <colgroup width="200">
                                        <tr>
                                            <td><%=position.getStartDate()%> - <%=position.getEndDate()%></td>
                                            <td><b><%=position.getPosition()%></b><br><%=position.getFunctions()%></td>
                                        </tr>
                                    </table>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </section>
        <jsp:include page="fragments/footer.jsp"/>
    </body>
</html>