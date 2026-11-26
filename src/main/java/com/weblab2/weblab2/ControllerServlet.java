package com.weblab2.weblab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("pointsToCheck", null);
        request.getSession().setAttribute("xError", null);
        request.getSession().setAttribute("yError", null);
        request.getSession().setAttribute("rError", null);
        if ("POST".equals(request.getMethod()) && request.getParameter("clearTable") != null) {
            request.getRequestDispatcher("/clear").forward(request, response);
            return;
        }
        if ("POST".equals(request.getMethod()) && request.getParameter("clickEvent") != null) {
            request.setAttribute("start", Instant.now());
            request.getRequestDispatcher("/checkArea").forward(request, response);
            return;
        }
        String[] rawX = request.getParameterValues("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        List<String> xs = (rawX == null) ? List.of() : Arrays.asList(rawX);
        y = (y == null) ? "" : y;
        r = (r == null) ? "" : r;
        request.setAttribute("start", Instant.now());
        try {
            PointList goodParams = ValidateFunctions.validateParams(xs, y, r);
            request.setAttribute("pointsToCheck", goodParams);
            request.getRequestDispatcher("/checkArea").forward(request, response);
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            List<String> errors = e.getErrors();
            request.getSession().setAttribute("xError", errors.get(0));
            request.getSession().setAttribute("yError", errors.get(1));
            request.getSession().setAttribute("rError", errors.get(2));
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}