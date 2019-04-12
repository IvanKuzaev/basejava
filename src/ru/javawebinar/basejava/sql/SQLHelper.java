package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private ConnectionFactory connectionFactory;

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
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
            throw new StorageException(e);
        }
    }
}
