package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ProcessQueryResult;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
            }
            sqlDeleteContacts(con, resume);
            sqlSaveContacts(con, resume);
            sqlDeleteSections(con, resume);
            sqlSaveSections(con, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            sqlSaveContacts(con, resume);
            sqlSaveSections(con, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.<Resume>executeSQLTransaction(con -> {
            Resume resume;
            try (PreparedStatement ps = con.prepareStatement("SELECT uuid, full_name FROM resume WHERE uuid=? ORDER BY full_name, uuid;")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                } else {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT resume_uuid, type, value FROM contact WHERE resume_uuid=? ORDER BY resume_uuid;")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    loadContacts(rs, resume);
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT resume_uuid, type, value FROM section WHERE resume_uuid=? ORDER BY resume_uuid;")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    loadSections(rs, resume);
                }
            }
            return resume;
        });
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
        return sqlHelper.<List<Resume>>executeSQLTransaction(con -> {
            Map<String, Resume> map = new HashMap<>();
            try (PreparedStatement ps = con.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name, uuid;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid.trim(), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT resume_uuid, type, value FROM contact ORDER BY resume_uuid;")) {
                ResultSet rs = ps.executeQuery();
                loadData(map, rs, this::loadContacts);
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT resume_uuid, type, value FROM section ORDER BY resume_uuid;")) {
                ResultSet rs = ps.executeQuery();
                loadData(map, rs, this::loadSections);
            }
            return new ArrayList<Resume>(map.values());
        });
    }

    @Override
    public int size() {
        ProcessQueryResult<Integer> pqr = (rs, uc) -> {
            rs.next();
            return rs.getInt(1);
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT COUNT(*) FROM resume;");
    }

    @FunctionalInterface
    private interface LoadData {
        boolean loadData(ResultSet rs, Resume resume) throws SQLException;
    }

    private void loadData(Map<String, Resume> map, ResultSet rs, LoadData ld) throws SQLException {
        boolean toContinue = rs.next();
        while (toContinue) {
            String uuid = rs.getString("resume_uuid");
            Resume resume = map.get(uuid);
            toContinue = ld.loadData(rs, resume);
        }
    }

    private void sqlDeleteContacts(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM contact WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void sqlSaveContacts(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
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

    private boolean loadContacts(ResultSet rs, Resume resume) throws SQLException {
        boolean toContinue;
        String uuid = resume.getUuid();
        do {
            resume.setContact(Contacts.valueOf(rs.getString("type")), rs.getString("value"));
        } while ((toContinue = rs.next()) && rs.getString("resume_uuid").trim().equals(uuid));
        return toContinue;
    }

    private void sqlDeleteSections(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM section WHERE resume_uuid=?;")) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void sqlSaveSections(Connection con, Resume resume) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?);")) {
            for (Map.Entry<Sections, AbstractResumeSection> e : resume.getSections().entrySet()) {
                Sections section = e.getKey();
                ps.setString(1, uuid);
                ps.setString(2, section.name());
                switch (section) {
                    case OBJECTIVE: case PERSONAL:
                        ps.setString(3, (String)resume.getSection(section).getData());
                        break;
                    case QUALIFICATIONS: case ACHIEVEMENTS:
                        List<String> list = (List<String>)resume.getSection(section).getData();
                        String[] strings = new String[list.size()];
                        list.<String>toArray(strings);
                        ps.setString(3, String.join("\n", strings));
                        break;
                    case EXPERIENCE: case EDUCATION:
                        //TODO: serialization BioSection into string by JSON
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private boolean loadSections(ResultSet rs, Resume resume) throws SQLException {
        boolean toContinue;
        String uuid = resume.getUuid();
        do {
            Sections section = Sections.valueOf(rs.getString("type"));
            switch (section) {
                case OBJECTIVE: case PERSONAL:
                    resume.setSection(Sections.valueOf(rs.getString("type")), new StringSection(rs.getString("value")));
                    break;
                case QUALIFICATIONS: case ACHIEVEMENTS:
                    resume.setSection(Sections.valueOf(rs.getString("type")), new StringListSection(rs.getString("value").split("\n")));
                    break;
                case EXPERIENCE: case EDUCATION:
                    //TODO: deserialization BioSection from string by JSON
                    break;
            }
        } while ((toContinue = rs.next()) && rs.getString("resume_uuid").trim().equals(uuid));
        return toContinue;
    }

}
