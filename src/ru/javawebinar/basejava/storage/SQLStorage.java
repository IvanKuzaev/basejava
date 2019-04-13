package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Contacts;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ProcessQueryResult;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            PreparedStatement ps;
            String uuid = resume.getUuid();
            ps = con.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?;");
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() < 1) {
                throw new NotExistStorageException(uuid);
            };
            ps.close();
            insertUpdateContacts(con, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>executeSQLTransaction(con -> {
            PreparedStatement ps;
            ps = con.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);");
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            ps.close();
            insertUpdateContacts(con, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        ProcessQueryResult<Resume> pqr = (rs, uc) -> {
            if (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
                do {
                    resume.setContact(Contacts.valueOf(rs.getString("type").trim()), rs.getString("value").trim());
                } while (rs.next());
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
                do {
                    resume.setContact(Contacts.valueOf(rs.getString("type")), rs.getString("value"));
                } while((toContinue = rs.next()) && rs.getString("uuid").equals(uuid));
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

    private void insertUpdateContacts(Connection con, Resume resume) throws SQLException {
        PreparedStatement ps;
        ps = con.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?) ON CONFLICT (resume_uuid, type) DO UPDATE SET value=EXCLUDED.value;");
        String uuid = resume.getUuid();
        for (Map.Entry<Contacts, String> e : resume.getContacts().entrySet()) {
            ps.setString(1, uuid);
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

}
