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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.ResumeTestData.fillDummyResume;

public class ResumeServlet extends HttpServlet {
    private SQLStorage storage;
    private Resume[] resumes = {
            new Resume("uuid04", "Ivanov Sergei"),
            new Resume("uuid03", "Petrov Vladimir"),
            new Resume("uuid02", "Petrov Vladimir"),
            new Resume("uuid01", "Sidorova Elena"),
    };

    public static String toHTML(Contacts contact, String value) {
        if (value != null) {
            switch (contact) {
                case SKYPE:
                    return "<a href='skype:" + value + "'>" + value + "</a>";
                case EMAIL:
                    return "<a href='mailto:" + value + "'>" + value + "</a>";
            }
            return value;
        } else {
            return "";
        }
    }

    public static String briefDate(LocalDate ld) {
        int m = ld.getMonthValue();
        int y = ld.getYear();
        if (y < 9999) {
            return (m < 10 ? "0" : "") + m + "/" + y;
        } else {
            return "сейчас";
        }
    }

    @Override
    public void init() {
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
        String[] sections = request.getParameterValues("section");
        Resume resume = new Resume(uuid, fullName);
        for (Contacts contactType : Contacts.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() > 0) {
                resume.setContact(contactType, value);
            }
        }
        String objective = request.getParameter("OBJECTIVE").trim();
        if (objective != null && objective.length() > 0) {
            resume.setSection(Sections.OBJECTIVE, new StringSection(objective));
        }
        String personal = request.getParameter("PERSONAL").trim();
        if (personal != null && personal.length() > 0) {
            resume.setSection(Sections.PERSONAL, new StringSection(personal));
        }
        String[] achievements = request.getParameterValues("ACHIEVEMENTS");
        if (achievements != null && achievements.length > 0) {
            resume.setSection(Sections.ACHIEVEMENTS, new StringListSection(achievements));
        }
        String[] qualifications = request.getParameterValues("QUALIFICATIONS");
        if (qualifications != null && qualifications.length > 0) {
            resume.setSection(Sections.QUALIFICATIONS, new StringListSection(qualifications));
        }
        String[] experience = request.getParameterValues("EXPERIENCE");
        if (experience != null && experience.length > 0) {
            List<LifeStage> lss = new ArrayList<>();
            for (String string : experience) {
                LifeStage lifeStage = JsonParser.<LifeStage>read(string, LifeStage.class);
                lss.add(lifeStage);
            }
            resume.setSection(Sections.EXPERIENCE, new BioSection(lss));
        }
        String[] education = request.getParameterValues("EDUCATION");
        if (education != null && education.length > 0) {
            List<LifeStage> lss = new ArrayList<>();
            for (String string : education) {
                LifeStage lifeStage = JsonParser.<LifeStage>read(string, LifeStage.class);
                lss.add(lifeStage);
            }
            resume.setSection(Sections.EDUCATION, new BioSection(lss));
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
