package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ProcessQueryResult;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeSQLCommand("DELETE FROM resume;");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = con.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?;")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() < 1) {
                    throw new NotExistStorageException(uuid);
                };
                sqlSaveContacts(con, resume);
                return null;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                sqlSaveContacts(con, resume);
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        ProcessQueryResult<Resume> pqr = (rs, uc) -> {
            if (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
                sqlLoadContacts(rs, resume);
                return resume;
            } else {
                throw new NotExistStorageException(uuid);
            }
        };
        return sqlHelper.<Resume>executeSQLCommand(pqr, "SELECT uuid, full_name, type, value FROM resume r LEFT JOIN contact c ON r.uuid=c.resume_uuid WHERE uuid=?;", uuid);
    }

    @Override
    public void delete(String uuid) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            if (uc == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        };
        sqlHelper.executeSQLCommand(pqr, "DELETE FROM resume WHERE uuid=?;", uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        ProcessQueryResult<List<Resume>> pqr = (rs, uc) -> {
            List<Resume> listResume = new ArrayList<>();
            boolean toContinue = rs.next();
            while (toContinue) {
                String uuid = rs.getString("uuid");
                Resume resume = new Resume(uuid.trim(), rs.getString("full_name").trim());
                toContinue = sqlLoadContacts(rs, resume);
                listResume.add(resume);
            };
            return listResume;
        };
        return sqlHelper.<List<Resume>>executeSQLCommand(pqr, "SELECT uuid, full_name, type, value FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid;");
    }

    @Override
    public int size() {
        ProcessQueryResult<Integer> pqr = (rs, uc) -> {
            rs.next();
            return rs.getInt(1);
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT COUNT(*) FROM resume;");
    }

    private int sqlSaveOrganization(Connection con, Organization organization) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO organization (title, description, weblink) VALUES (?, ?, ?, ?) ON CONFLICT DO UPDATE SET description=EXCLUDED.description, weblink=EXCLUDED.weblink;")) {
            ps.setString(1, organization.getTitle());
            ps.setString(2, organization.getDescription());
            ps.setString(3, organization.getWebLink());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        }
    }

    private Organization sqlLoadOrganization(Connection con, int id) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM organization WHERE id=?;")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Organization(rs.getString("title"), rs.getString("description"), rs.getString("weblink"));
            } else {
                return null;
            }
        }
    }
    private void sqlSaveBioSection(Connection con, Sections section, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        String type = section.name();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO biography (resume_uuid, type, organization_id, start_date, end_date, title, description) VALUES (?, ?, ?, ?, ?, ?, ?);")) {
            for (LifeStage ls : (List<LifeStage>)resume.getSection(section).getData()) {
                int organization_id = sqlSaveOrganization(con, ls.getOrganization());
                for (LifePeriod lp : ls.getData()) {
                    ps.setString(1, uuid);
                    ps.setString(2, type);
                    ps.setInt(3, organization_id);
                    ps.setDate(4,Date.valueOf(lp.getStartDate()));
                    ps.setDate(5, Date.valueOf(lp.getEndDate()));
                    ps.setString(6, lp.getTitle());
                    ps.setString(7, lp.getDescription());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }

    private BioSection sqllLoadBioSection(Connection con, Sections section, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        String type = section.name();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM biography WHERE resume_uuid=? AND type=? ORDER BY start_date DESC;")) {
            ps.setString(1, uuid);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();
            int organizationId;
            int organizationIdPrev = -1;
            while (rs.next()) {
                List<LifePeriod> llp = new ArrayList<>();
                organizationId = rs.getInt("organization_id");
                Organization organization = sqlLoadOrganization(con, organizationId);
                LifePeriod lp = new LifePeriod(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate(), rs.getString("title"), rs.getString("description"));
                llp.add(lp);

            }

        }
        return null;
    }

    private boolean sqlLoadContacts(ResultSet rs, Resume resume) throws SQLException {
        if (rs.getString("type") != null) {
            boolean toContinue;
            String uuid = resume.getUuid();
            do {
                resume.setContact(Contacts.valueOf(rs.getString("type")), rs.getString("value"));
            } while ((toContinue = rs.next()) && rs.getString("uuid").trim().equals(uuid));
            return toContinue;
        } else {
            return rs.next();
        }
    }

    private void sqlSaveContacts(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM contact WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<Contacts, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private AbstractResumeSection stringToSection(Sections section, String string) {
        AbstractResumeSection resumeSection = null;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                resumeSection = new StringSection(string);
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                String[] strings = string.split("\n");
                resumeSection = new StringListSection(strings);
                break;
            case EXPERIENCE:
            case EDUCATION:
                break;
        }
        return resumeSection;
    }

    private String sectionToString(Sections section, Resume resume) {
        String string = "";
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                string = (String)resume.getSection(section).getData();
                break;
            case ACHIEVEMENTS:
            case QUALIFICATIONS:
                List<String> list = (List<String>)resume.getSection(section).getData();
                for (int i = 0; i < list.size(); i++) {
                    string += list.get(i) + "\n";
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                break;
        }
        return string;
    }

    private void sqlSaveSections(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM section WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<Sections, AbstractResumeSection> e : resume.getSections().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, sectionToString(e.getKey(), resume));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
