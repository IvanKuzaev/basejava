package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SQLStorage;
import ru.javawebinar.basejava.util.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.javawebinar.basejava.ResumeTestData.fillDummyResume;

public class ResumeServlet extends HttpServlet {
    private SQLStorage storage;

    @Override
    public void init() {
        Resume[] resumes = {
                new Resume("uuid05", "Nazarova Olga"),
                new Resume("uuid04", "Ivanov Sergei"),
                new Resume("uuid03", "Petrov Vladimir"),
                new Resume("uuid02", "Petrov Vladimir"),
                new Resume("uuid01", "Sidorova Elena"),
        };
        org.postgresql.Driver driver = new org.postgresql.Driver();//needs to load jbdc driver, otherwise it don't work in tomcat
        storage = new SQLStorage(Config.dbUrl, Config.dbUser, Config.dbPassword);
        storage.clear();
        for (int i = 0; i < resumes.length; i++) {
            fillDummyResume(resumes[i], i);
            storage.save(resumes[i]);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid").trim();
        String fullName = request.getParameter("fullName");
        Resume resume = new Resume(uuid, fullName);
        for (Contacts contactType : Contacts.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() > 0) {
                resume.setContact(contactType, value);
            }
        }
        for (Sections sectionType : Sections.values()) {
            String[] sectionStrings = request.getParameterValues(sectionType.name());
            if (sectionStrings != null) {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        if (sectionStrings[0].trim().length() > 0) {
                            resume.setSection(sectionType, new StringSection(sectionStrings[0]));
                        }
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, new StringListSection(sectionStrings));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(sectionType, new BioSection(Arrays.stream(sectionStrings).map((string) -> { return JsonParser.<LifeStage>read(string, LifeStage.class);}).collect(Collectors.toList())));
                        break;
                }
            }
        }
        String sqlAction = request.getParameter("action");
        switch(sqlAction) {
            case "update":
                storage.update(resume);
                break;
            case "save":
                storage.save(resume);
                break;
        }
        response.sendRedirect("resumes");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" :
                storage.delete(uuid);
                response.sendRedirect("resumes");
                return;
            case "view" :
            case "edit" :
                resume = storage.get(uuid);
                request.setAttribute("action", "update");
                break;
            case "create" :
                resume = new Resume("");
                request.setAttribute("action", "save");
                break;
            default:
                throw new IllegalArgumentException("Action '" + action + "' is illegal.");
        }
        request.setAttribute("resume", resume);
        request
                .getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
