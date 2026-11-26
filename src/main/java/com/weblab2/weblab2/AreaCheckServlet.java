package com.weblab2.weblab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] xValues = request.getParameterValues("x");
        String yValue = request.getParameter("y");
        String rValue = request.getParameter("r");
        double y = Double.parseDouble(yValue);
        double r = Double.parseDouble(rValue);
        HttpSession session = request.getSession();
        PointList pointList = (PointList) session.getAttribute("pointList");
        if (pointList == null) {
            pointList = new PointList();
        }

        Instant start = (Instant) request.getAttribute("start");
        List<Point> pointsToCheck = new ArrayList<>();
        for (String xValue : xValues) {
            double x = Double.parseDouble(xValue);
            boolean result = ValidateFunctions.checkIfInsideFigure(x, y, r);
            Point point = new Point(x, y, r, result);
            pointList.getPointList().add(point);
            pointsToCheck.add(point);
            point.setExecTime(start);
        }
        session.setAttribute("pointList", pointList);
        session.setAttribute("pointsToCheck", pointsToCheck);
        if (request.getParameter("clickEvent") != null){
            response.sendRedirect(request.getContextPath() + "/");
        }
        else {
            request.getRequestDispatcher("/answer.jsp").forward(request, response);
        }
    }
}