<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add/Edit Meal</title>
</head>
<body>
<form method="POST" name="frmAddMeal">

    Id: <input type="text" readonly="readonly" name="id"
               value="<c:out value="${meal.id}" />" /> <br />
    DateTime: <input
        type="datetime-local" name="datetime-local"
        value="<c:out value="${meal.dateTime}" />" /> <br />
    Description: <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories: <input type="text" name="calories"
                     value="<c:out value="${meal.calories}" />" /> <br />

    <input type="submit" value="Submit" />

</form>
</body>
</html>