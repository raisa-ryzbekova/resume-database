package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(
                        "<html>\n" +
                                "<table border=\"1\">" +
                                "<tr>\n" +
                                "<td><center>" + "uuid" + "</center></td>\n" +
                                "<td><center>" + "Имя" + "</center></td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td>" + uuid + "</td>\n" +
                                "<td>" + sqlStorage.get(uuid).getFullName() + "</td>\n" +
                                "</tr>\n" +
                                "</table>\n" +
                                "</html>\n"
                );
            }
        } else {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(
                        "<html>" +
                                "<h3>Список резюме</h3>\n" +
                                "<table border=\"1\">\n" +
                                "<tr>\n" +
                                "<td><center>" + "uuid" + "</center></td>\n" +
                                "<td><center>" + "Имя" + "</center></td>\n" +
                                "</tr>\n" +
                                "<tr>\n");
                for (Resume resume : sqlStorage.getAllSorted()) {
                    writer.write(
                            "<tr>" +
                                    "<td>" + resume.getUuid() + "</td>\n" +
                                    "<td>" + resume.getFullName() + "</td>\n" +
                                    "</tr>");
                }
                writer.write(
                        "</table>\n" +
                                "</html>\n"
                );
            }
        }
    }
}