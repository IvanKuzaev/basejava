package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Contacts;
import ru.javawebinar.basejava.model.LifeStage;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Sections;
import ru.javawebinar.basejava.storage.SQLStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.ResumeTestData.fillDummyResume;

public class ResumeServlet extends HttpServlet {
    private SQLStorage storage;
    private Resume[] resumes = {
            new Resume("uuid04", "Ivanov Sergei"),
            new Resume("uuid03", "Petrov Vladimir"),
            new Resume("uuid02", "Petrov Vladimir"),
            new Resume("uuid01", "Sidorova Elena"),
    };

    public ResumeServlet() {
        super();
        org.postgresql.Driver driver = new org.postgresql.Driver();//needs to load jbdc driver, otherwise it don't work in tomcat
        storage = new SQLStorage(Config.dbUrl, Config.dbUser, Config.dbPassword);
        storage.clear();
        for (int i = 0; i < resumes.length; i++) {
            fillDummyResume(resumes[i], i);
            storage.save(resumes[i]);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        List<Resume> listResume;
        boolean notExist = false;
        if (uuid == null) {
            listResume = storage.getAllSorted();
        } else {
            listResume = new ArrayList<>();
            Resume resume = null;
            try {
                resume = storage.get(uuid);
            } catch (NotExistStorageException e) {
                notExist = true;
            }
            if (resume != null) {
                listResume.add(resume);
            }
        }
        int size = listResume.size();
        PrintWriter out = response.getWriter();
        if (uuid == null ) {
            out.write("<h2>List of all resumes in database</h2>");
        } else {
            out.write("<h2>Resume " + uuid + " in database</h2>");
        }
        if (size > 0) {
            out.write("Found " + size + " resumes.");
            out.write("<table border=\"3\">");
            for (int i = 0; i < size; i++) {
                Resume resume = listResume.get(i);
                out.write("<tr><td bgcolor=\"lightgrey\" colspan=\"2\">" + resume.getFullName() +  " (" + resume.getUuid() +")</td></tr>");
                out.write("<tr><td  width=\"200\">Contacts</td><td>");
                for (Map.Entry<Contacts, String> e : resume.getContacts().entrySet()) {
                    out.write(e.getKey().getTitle() + " : " + e.getValue() + "<br>");
                }
                out.write("</td></tr>");
                if (resume.getSections().containsKey(Sections.OBJECTIVE)) {
                    out.write("<tr><td>Objective</td><td>" + resume.getSection(Sections.OBJECTIVE).getData() + "</td></tr>");
                }
                if (resume.getSections().containsKey(Sections.PERSONAL)) {
                    out.write("<tr><td>Personal</td><td>" + resume.getSection(Sections.PERSONAL).getData() + "</td></tr>");
                }
                if (resume.getSections().containsKey(Sections.QUALIFICATIONS)) {
                    out.write("<tr><td>Qualifications</td><td><ul>");
                    for (String string : (List<String>)resume.getSection(Sections.QUALIFICATIONS).getData()) {
                        out.write("<li>" + string + "</li>");
                    }
                    out.write("</ul></td></tr>");
                }
                if (resume.getSections().containsKey(Sections.ACHIEVEMENTS)) {
                    out.write("<tr><td>Achievements</td><td><ul>");
                    for (String string : (List<String>)resume.getSection(Sections.ACHIEVEMENTS).getData()) {
                        out.write("<li>" + string + "</li>");
                    }
                    out.write("</ul></td></tr>");
                }
                if (resume.getSections().containsKey(Sections.EXPERIENCE)) {
                    out.write("<tr><td>Experience</td><td>");
                    for (LifeStage lifeStage : (List<LifeStage>)resume.getSection(Sections.EXPERIENCE).getData()) {
                        out.write("<li>" + lifeStage + "</li>");
                    }
                    out.write("</td></tr>");
                }
                if (resume.getSections().containsKey(Sections.EDUCATION)) {
                    out.write("<tr><td>Education</td><td>");
                    for (LifeStage lifeStage : (List<LifeStage>)resume.getSection(Sections.EDUCATION).getData()) {
                        out.write("<li>" + lifeStage + "</li>");
                    }
                    out.write("</td></tr>");
                }
            }


            out.write("</table>");
        } else {
            out.write("No resumes found.");
        }

    }
}
