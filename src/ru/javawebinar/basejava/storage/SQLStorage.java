package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.ProcessQueryResult;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.sql.SQLHelper.executeSQLCommand;

public class SQLStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            return null;
        };
        executeSQLCommand(connectionFactory, pqr, "DELETE FROM resume;");
    }

    @Override
    public void update(Resume resume) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            if (uc == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        };
        executeSQLCommand(connectionFactory, pqr, "UPDATE resume SET full_name=? WHERE uuid=?;", resume.getFullName(), resume.getUuid());
    }

    @Override
    public void save(Resume resume) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            return null;
        };
        try {
            executeSQLCommand(connectionFactory, pqr, "INSERT INTO resume (uuid, full_name) VALUES (?, ?);", resume.getUuid(), resume.getFullName());
        } catch(StorageException e) {
            throw new ExistStorageException(e.getMessage());
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
        return executeSQLCommand(connectionFactory, pqr, "SELECT uuid, full_name FROM resume WHERE uuid=?;", uuid);
    }

    @Override
    public void delete(String uuid) {
        ProcessQueryResult<Void> pqr = (rs, uc) -> {
            if (uc == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        };
        executeSQLCommand(connectionFactory, pqr, "DELETE FROM resume WHERE uuid=?;", uuid);
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
        return executeSQLCommand(connectionFactory, pqr, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid;");
    }

    @Override
    public int size() {
        ProcessQueryResult<Integer> pqr = (rs, uc) -> {
            rs.next();
            return rs.getInt(1);
        };
        return executeSQLCommand(connectionFactory, pqr, "SELECT COUNT(*) FROM resume;");
    }
}
