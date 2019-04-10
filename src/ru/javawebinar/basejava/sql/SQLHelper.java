package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class SQLHelper {
    public static <T> T executeSQLCommand(ConnectionFactory connectionFactory, ProcessQueryResult<T> pqr, String sql, Object ... args) {
        try (Connection con = connectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof String) {
                    ps.setString(i + 1, (String)args[i]);
                }
                if (args[i] instanceof Integer) {
                    ps.setInt(i + 1, (Integer)args[i]);
                }
                if (args[i] instanceof Long) {
                    ps.setLong(i + 1, (Long)args[i]);
                }
                if (args[i] instanceof Date) {
                    ps.setDate(i + 1, new java.sql.Date(((Date)args[i]).getTime()));
                }
                if (args[i] instanceof Double) {
                    ps.setDouble(i + 1, (Double)args[i]);
                }
                if (args[i] instanceof Float) {
                    ps.setFloat(i + 1, (Float)args[i]);
                }
                if (args[i] instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean)args[i]);
                }
            }
            ps.execute();
            return pqr.process(ps.getResultSet(), ps.getUpdateCount());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
