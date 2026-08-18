package com.eric.labo1;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/bonjour")
public class BonjourServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><meta charset=\"UTF-8\"><title>Labo 1</title></head>");
        out.println("<body>");
        out.println("<h1>Bonjour, Jakarta EE !</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
