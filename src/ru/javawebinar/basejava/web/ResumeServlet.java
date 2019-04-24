package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SQLStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                out.write("<tr><td width=\"250\">" + resume.getFullName() +"</td>");
                out.write("<td width=\"250\">" + resume.getUuid() +"</td></tr>");
            }
            out.write("</table>");
        } else {
            out.write("No resumes found.");
        }
    }
}
