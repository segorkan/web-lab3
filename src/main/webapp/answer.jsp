<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.weblab2.weblab2.Point" %>
<%@ page import="java.util.List" %>
<%
    List<Point> pointsToCheck = (List<Point>) session.getAttribute("pointsToCheck");
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат проверки</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"/>
</head>
<body>
<header id="page-header">
    <div>Ярков Сергей</div>
    <div>P3208</div>
    <div class="variant-row"><div>Вариант 469547</div></div>
</header>

<canvas id="graph" width="600" height="600"></canvas>
<div class="table-box">
    <table cellpadding="10" id="checks">
        <thead>
        <tr class="table-head">
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
            <th>Дата и время проверки</th>
            <th>Время выполнения</th>
        </tr>
        </thead>
        <tbody id="checks-body">
        <% for (Point p : pointsToCheck) { %>
            <tr>
                <td><%= p.getX() %></td>
                <td><%= p.getY() %></td>
                <td><%= p.getR() %></td>
                <td class="<%= p.isResult() ? "success-text" : "fail-text" %>">
                    <%= p.isResult() ? "Попадание" : "Промах" %>
                </td>
                <td><%= p.getTime() %></td>
                <td><%= p.getExecTime() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
<script src="${pageContext.request.contextPath}/draw.js"></script>
<script>
    const canvas = document.getElementById("graph");
    const context = canvas.getContext("2d");
    <%
    if (pointsToCheck != null && !pointsToCheck.isEmpty()) {
        double currentR = pointsToCheck.get(0).getR();
    %>
        context.lineWidth = 1;
        context.fillStyle = "#378CFA";
        drawFigure(context, <%= currentR %>);
        context.strokeStyle = "black";
        drawCoords(canvas, context);
    <%
        for (Point p : pointsToCheck) {
    %>
            placePoint(context, <%= p.getX() %>, <%= p.getY() %>);
    <%
        }
    }
    session.setAttribute("pointsToCheck", null);
    %>
</script>
<p><a href="${pageContext.request.contextPath}/" style="font-size: 2rem">Возвращение к веб-форме.</a></p>
<style>
    a {
        color: #ffffff;
    }

    a:visited {
        color: #ffffff;
    }
</style>
</body>
</html>