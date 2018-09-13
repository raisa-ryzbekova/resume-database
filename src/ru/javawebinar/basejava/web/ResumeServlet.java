package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = sqlStorage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType contactType : ContactType.values()) {
            String contactValue = request.getParameter(contactType.name());
            if (contactValue != null && contactValue.trim().length() != 0) {
                resume.setContact(contactType, contactValue);
            } else {
                resume.getContacts().remove(contactType);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String sectionValue = request.getParameter(sectionType.name());
            if (sectionValue != null && sectionValue.trim().length() != 0) {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(sectionType, new TextSection(sectionValue));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, new ListSection(sectionValue));
                        break;
                }
            } else {
                resume.getSections().remove(sectionType);
            }
        }
        sqlStorage.update(resume);
        response.sendRedirect("resume");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = null;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = sqlStorage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}