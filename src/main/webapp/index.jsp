<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.weblab2.weblab2.Point" %>
<%@ page import="com.weblab2.weblab2.PointList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ArrayList" %>
<%
    PointList pointList = (PointList) session.getAttribute("pointList");
    List<Point> points = (pointList != null) ? new ArrayList<>(pointList.getPointList()) : new ArrayList<>();
    Collections.reverse(points);
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лаб. 3</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css"/>
</head>

<body>
<table id="layout-table">
    <tr>
        <td colspan="2">
            <header id="page-header">
                <div>Ярков Сергей</div>
                <div>P3208</div>
                <div class="variant-row">
                    <div>Вариант 469547</div>
                    <label class="error-label" id="bad_error"></label>
                </div>
            </header>
        </td>
    </tr>
    <tr>
        <td>
            <form class="box" id="check-form" method="post" action="controller" novalidate>
                <div class="input-group">
                    <label for="x-select">Выберите параметр X:<br></label>
                    <fieldset id="x-checkbox" style="border: transparent;">
                        <label><input type="checkbox" name="x" value="-5">-5</label>
                        <label><input type="checkbox" name="x" value="-4">-4</label>
                        <label><input type="checkbox" name="x" value="-3">-3</label>
                        <label><input type="checkbox" name="x" value="-2">-2</label>
                        <label><input type="checkbox" name="x" value="-1">-1</label>
                        <label><input type="checkbox" name="x" value="0">0</label>
                        <label><input type="checkbox" name="x" value="1">1</label>
                        <label><input type="checkbox" name="x" value="2">2</label>
                        <label><input type="checkbox" name="x" value="3">3</label>
                    </fieldset>
                    <div class="error-label" id="x-error"><p>${sessionScope.xError eq 'Верное значение.' ? '' : sessionScope.xError}</p></div>
                </div>

                <div class="input-group">
                    <label for="y-input">Выберите параметр Y:<br></label>
                    <input id="y-input" name="y" type="text" placeholder="-5 ... 3" size="30">
                    <div class="error-label" id="y-error"><p>${sessionScope.yError eq 'Верное значение.' ? '' : sessionScope.yError}</p></div>
                </div>

                <div class="input-group">
                    <label for="r-input">Выберите параметр R:<br></label>
                    <input id="r-input" name="r" type="text" placeholder="2 ... 5" size="30">
                    <div class="error-label" id="r-error"><p>${sessionScope.rError eq 'Верное значение.' ? '' : sessionScope.rError}</p></div>
                </div>

                <div class="send-group">
                    <button type="submit">Проверка</button>
                </div>

                <div class="clear-group">
                    <button name="clearForm" type="button">Очистка формы</button>
                </div>

                <div class="tablebutton-group">
                    <button name="clearTable" type="button">Очистка таблицы</button>
                </div>
            </form>
        </td>
        <td>
            <canvas id="graph" width="600" height="600"></canvas>
        </td>
    </tr>
    <tr>
        <td colspan="2">
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
                    <% for (Point p : points) { %>
                        <tr>
                            <td><%= p.getX() %></td>
                            <td><%= p.getY() %></td>
                            <td><%= p.getR() %></td>
                            <td class=<%= p.isResult() ? "success-text" : "fail-text" %>>
                                <%= p.isResult() ? "Попадание" : "Промах" %>
                            </td>
                            <td><%= p.getTime() %></td>
                            <td><%= p.getExecTime() %></td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
</table>
<script src="${pageContext.request.contextPath}/draw.js"></script>
<script src="${pageContext.request.contextPath}/script.js"></script>
<script>
    <%
        List<Point> drawPoints = (List<Point>) session.getAttribute("pointsToCheck");
        if (drawPoints != null && !drawPoints.isEmpty()) {
            double currentR = drawPoints.get(0).getR();
    %>
            context.clearRect(0, 0, canvas.width, canvas.height);
            context.lineWidth = 1;
            context.fillStyle = "#378CFA";
            drawFigure(context, <%= currentR %>);
            context.strokeStyle = "black";
            drawCoords(canvas, context);
    <%
            for (Point p : drawPoints) {
    %>
                placePoint(context, <%= p.getX() %>, <%= p.getY() %>);
    <%
            }
        }
    %>
</script>
</body>
</html>