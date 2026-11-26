package com.weblab2.weblab2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/clear")
public class ClearServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PointList pointList = (PointList) session.getAttribute("pointList");
        if (pointList != null) {
            pointList.getPointList().clear();
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}