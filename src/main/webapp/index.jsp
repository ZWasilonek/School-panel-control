<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Code School</title>
</head>
<body>
    <%@include file="header.jsp"%>

    <table>
    <c:forEach items="${solutions}" var="solution">
        <tr>
            <td>
                ${solution.updated}
            </td>
            <td>
                <c:out value="${solution.exercise.title}"/>
            </td>
            <td>
                <c:out value="${solution.user.userName}"/>
            </td>
        </tr>
    </c:forEach>
    </table>

    <%@include file="footer.jsp"%>
</body>
</html>
