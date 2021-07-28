package com.hrant.repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    int insert(DataSource dataSource, T t) throws SQLException;

    int delete(DataSource dataSource, T t) throws SQLException;

    int deleteById(DataSource dataSource, int id) throws SQLException;

    List<T> getAll(DataSource dataSource) throws SQLException;

    T findById(DataSource dataSource, int id) throws SQLException;

    int update(int id, DataSource dataSource, T t) throws SQLException;
}

