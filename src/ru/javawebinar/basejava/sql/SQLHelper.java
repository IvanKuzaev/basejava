package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private ConnectionFactory connectionFactory;

    @FunctionalInterface
    public interface ConnectionHandle<T> {
        T execute(Connection con) throws SQLException;
    }

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    private StorageException toStorageException(SQLException e) {
        if (e.getSQLState().equals("23505")) {//primary key conflict
            return new ExistStorageException(e);
        } else {
            return new StorageException(e);
        }
    }

    public <T> T executeSQLCommand(ProcessQueryResult<T> pqr, String sql, Object ... args) {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
            return pqr.process(ps.getResultSet(), ps.getUpdateCount());
        } catch (SQLException e) {
            throw toStorageException(e);
        }
    }

    public void executeSQLCommand(String sql, Object ... args) {
        this.<Void>executeSQLCommand((rs, uc) -> { return null; }, sql, args);
    }

    public <T> T executeSQLTransaction(ConnectionHandle<T> ch) {
        try (Connection con = connectionFactory.getConnection()) {
            try {
                con.setAutoCommit(false);
                T result = ch.execute(con);
                con.commit();
                return result;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw toStorageException(e);
        }
    }
}
