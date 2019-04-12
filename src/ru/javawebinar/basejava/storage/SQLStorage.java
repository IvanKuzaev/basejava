package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ProcessQueryResult;
import ru.javawebinar.basejava.sql.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            return null;
        };
        sqlHelper.executeSQLCommand(pqr, "DELETE FROM resume;");
    }

    @Override
    public void update(Resume resume) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            if (uc == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        };
        sqlHelper.executeSQLCommand(pqr, "UPDATE resume SET full_name=? WHERE uuid=?;", resume.getFullName(), resume.getUuid());
    }

    @Override
    public void save(Resume resume) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            return null;
        };
        try {
            sqlHelper.executeSQLCommand(pqr, "INSERT INTO resume (uuid, full_name) VALUES (?, ?);", resume.getUuid(), resume.getFullName());
        } catch(StorageException e) {
            if (((SQLException)e.getCause()).getSQLState().equals("23505")) {//primary key conflict
                throw new ExistStorageException(e.getMessage());
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        ProcessQueryResult<Resume> pqr = (rs, uc) -> {
            if (rs.next()) {
                return new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
            } else {
                throw new NotExistStorageException(uuid);
            }
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT uuid, full_name FROM resume WHERE uuid=?;", uuid);
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
            while (rs.next()) {
                listResume.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim()));
            }
            return listResume;
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid;");
    }

    @Override
    public int size() {
        ProcessQueryResult<Integer> pqr = (rs, uc) -> {
            rs.next();
            return rs.getInt(1);
        };
        return sqlHelper.executeSQLCommand(pqr, "SELECT COUNT(*) FROM resume;");
    }
}
