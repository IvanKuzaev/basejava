package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SQLStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
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

    private static String briefDate(LocalDate ld) {
        int m = ld.getMonthValue();
        int y = ld.getYear();
        if (y < 9999) {
            return (m < 10 ? "0" : "") + m + "/" + y;
        } else {
            return "сейчас";
        }
    }

    private static String sectionTitle(Sections section) {
        switch (section) {
            case OBJECTIVE:
                return "<br><h3 style='display:inline-block'>Позиция</h3>\n";
            case PERSONAL:
                return "<br><h3 style='display:inline-block'>Личные качества</h3>\n";
            case QUALIFICATIONS:
                return "<br><h3 style='display:inline-block'>Квалификация</h3>\n";
            case ACHIEVEMENTS:
                return "<br><h3 style='display:inline-block'>Достижения</h3>\n";
            case EXPERIENCE:
                return "<br><h3 style='display:inline-block'>Опыт работы</h3>\n";
            case EDUCATION:
                return "<br><h3 style='display:inline-block'>Образование</h3>\n";
        }
        return "";
    }

    public static String toViewHTML(Sections section, AbstractResumeSection resumeSection) {
        String value = "";
        if (resumeSection != null) {
            value += sectionTitle(section) + "<br>";
            switch (section) {
                case OBJECTIVE:
                case PERSONAL:
                    value += "<p>" + (String)resumeSection.getData() + "</p>\n";
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENTS:
                    value += "<ul>\n";
                    for (String string : (List<String>)resumeSection.getData()) {
                        value += "<li>" + string + "</li>\n";
                    }
                    value += "</ul>\n";
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    for (LifeStage ls : (List<LifeStage>)resumeSection.getData()) {
                        Organization organization = ls.getOrganization();
                        value += "<a href='" + organization.getWebLink() + "'><h4>" + organization.getTitle() + "</h4></a>\n";
                        value += "<table>";
                        List<LifePeriod> lps = (List<LifePeriod>)ls.getData();
                        lps.sort(Comparator.comparing(LifePeriod::getStartDate).reversed());
                        for (LifePeriod lp : lps) {
                            value += "<tr>\n";
                            value += "<td width='150px' valign='top'>" + briefDate(lp.getStartDate()) + "-" + briefDate(lp.getEndDate()) + "</td>";
                            value += "<td><b>" + lp.getTitle() + "</b>";
                            String description = lp.getDescription();
                            if (description != null) {
                                value += "<br>" + description;
                            }
                            value += "</td>\n";
                            value += "</tr>\n";
                        }
                        value += "</table>\n";
                    }
                    break;
            }
        }
        return value;
    }

    public static String toEditHTML(Sections section, AbstractResumeSection resumeSection) {
        String name = section.name();
        String value = "";
        value += sectionTitle(section);
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                value += "<br>";
                value += "<input type='text' name='" + name + "' value='";
                if (resumeSection != null) {
                    value += (String)resumeSection.getData();
                }
                value += "'>\n";
                break;
            case QUALIFICATIONS:
            case ACHIEVEMENTS:
                value += "<br>";
                value += "<input type='text' name='item" + name + "'>\n";
                value += "<img src='/resumes-webapp/img/add.png' onclick='addItem(" + name + ", item" + name + ")'>";
                value += "<img src='/resumes-webapp/img/delete.png' onclick='removeItem(" + name + ")'><br>\n";
                value += "<select name='" + name + "' size='5'>\n";
                if (resumeSection != null) {
                    for (String string : (List<String>) resumeSection.getData()) {
                        value += "<option value='" + string + "'>" + string + "</option>\n";
                    }
                }
                value += "</select>\n";
                break;
            case EXPERIENCE:
            case EDUCATION:
                value += "<img src='/resumes-webapp/img/add.png' onclick='addOrganization()'>\n";
                value += "<br>";
                if (resumeSection != null) {
                    int count1 = 0;
                    for (LifeStage ls : (List<LifeStage>)resumeSection.getData()) {
                        count1++;
                        Organization organization = ls.getOrganization();
                        value += "<fieldset>\n";
                        value += "<legend>\n";
                        value += "<label for='organization" + count1 + "'>Организация</label>";
                        value += "<input type='text' name='organization" + count1 + "' value='" + organization.getTitle() + "'>\n";
                        value += "<label for='weblink" + count1 + "'>Сайт организации</label>";
                        value += "<input type='url name='weblink" + count1 + "' value='" + organization.getWebLink() + "'>\n";
                        value += "<img src='/resumes-webapp/img/delete.png' onclick='removeOrganization()'>\n";
                        value += "</legend>\n";
                        value += "<table>\n";
                        List<LifePeriod> lps = (List<LifePeriod>) ls.getData();
                        lps.sort(Comparator.comparing(LifePeriod::getStartDate).reversed());
                        int count2 = 0;
                        for (LifePeriod lp : lps) {
                            count2++;
                            String nameStartDate = "startDate_" + count1 + "_" + count2;
                            String nameEndDate = "endDate_" + count1 + "_" + count2;
                            String namePosition = "position_" + count1 + "_" + count2;
                            String nameDescription = "description_" + count1 + "_" + count2;
                            value += "<tr>\n";
                            value += "<td width='350px' valign='top'><label for='" + nameStartDate + "' width='20px'>с</label><input type='date' name='" + nameStartDate + "' value='" + lp.getStartDate() + "'><label for='" + nameEndDate + "' width='20px'>по</label><input type='date' name='" + nameEndDate + "' value='" + lp.getEndDate() + "'></td>";
                            value += "<td><label for='" + namePosition + "' width='80px'>позиция</label><input type='text' name='" + namePosition + "' value='" + lp.getTitle() + "'><br><label for='" + nameDescription + "' width='80px'>описание</label><input type='text' name='" + nameDescription + "' ";
                            String description = lp.getDescription();
                            if (description != null) {
                                value += "value='" + description + "'";
                            }
                            value += ">\n";
                            value += "</td>\n";
                            value += "</tr>\n";
                        }
                        value += "</table>\n";
                        value += "</fieldset>";
                    }
                }
            break;
        }
        return value;
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
        storage.update(resume);
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
