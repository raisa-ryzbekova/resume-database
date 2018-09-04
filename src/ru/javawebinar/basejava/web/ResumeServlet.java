package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage sqlStorage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            response.getWriter().write(uuid + " " + sqlStorage.get(uuid).getFullName());
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.println("<html>");
                out.println("<table border=\"1\">");
                for (Resume resume : sqlStorage.getAllSorted()) {
                    out.println("<tr>");
                    out.println("<td>" + resume.getUuid() + "</td>");
                    out.println("<td>" + resume.getFullName() + "</td>");
                    out.println("</tr>");
                }
                out.println("/table");
                out.println("</html>");
            }
        }
    }
}