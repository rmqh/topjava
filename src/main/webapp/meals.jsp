<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<%--<jsp:useBean id="mealWithExceedList" scope="request" class="java.util.ArrayList"/>--%>
<%--<jsp:useBean id="mealWithExceed" scope="request" class="ru.javawebinar.topjava.model.MealWithExceed"/>--%>

<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<p><a href="?action=add">Add</a></p>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="mealWithExcess" items="${mealWithExcessList}">
        <tr style="color: ${mealWithExcess.excess == true ? "red" : "green"};">
            <td>${fn:replace(mealWithExcess.dateTime, "T", " ")   }</td>
            <td>${mealWithExcess.description}</td>
            <td>${mealWithExcess.calories}</td>
            <td><a href="?action=update&mealId=<c:out value="${mealWithExcess.id}"/>">Update</a></td>
            <td><a href="?action=delete&mealId=<c:out value="${mealWithExcess.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html> 