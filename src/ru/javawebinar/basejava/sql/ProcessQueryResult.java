package ru.javawebinar.basejava.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ProcessQueryResult<T> {
    T process(ResultSet rs, int updateCount) throws SQLException;
}
