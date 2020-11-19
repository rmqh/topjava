<%@ page import="ru.javawebinar.topjava.web.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users</h2>
<p>Authorized user id: <%=SecurityUtil.authUserId()%></p>
<hr>
<p>Test users order</p>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Enabled</th>
        <th>Registered</th>
        <th>Roles</th>
        <th>Calories per day</th>
    </tr>
    </thead>
    <c:forEach items="${users}" var="user">
        <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.enabled}</td>
            <td>${user.registered}</td>
            <td>${user.roles}</td>
            <td>${user.caloriesPerDay}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>