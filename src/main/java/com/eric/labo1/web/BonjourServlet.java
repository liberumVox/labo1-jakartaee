package com.eric.labo1.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Livrable 1 : servlet Jakarta EE qui repond « Bonjour, Jakarta EE ! » sur une requete GET.
 *
 * Namespace jakarta.* (et non javax.*) : exige Tomcat 10.1+ / Jakarta EE 10.
 * L'annotation @WebServlet remplace la declaration <servlet-mapping> du web.xml.
 *
 * URL apres deploiement : http://localhost:8080/labo1/bonjour
 */
@WebServlet(name = "bonjourServlet", urlPatterns = {"/bonjour"})
public class BonjourServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("""
                    <!DOCTYPE html>
                    <html lang="fr">
                    <head>
                        <meta charset="UTF-8">
                        <title>Labo 1 - Jakarta EE</title>
                    </head>
                    <body>
                        <h1>Bonjour, Jakarta EE !</h1>
                        <p>Servlet deployee sur Tomcat via un projet Maven.</p>
                    </body>
                    </html>
                    """);
        }
    }
}
