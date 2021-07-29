package com.hrant.repository;

import com.hrant.model.Department;
import com.hrant.util.Converter;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository implements Repository<Department> {

    private static List<Department> departments = new ArrayList<>();

    @Override
    public int insert(DataSource dataSource, Department o) throws SQLException {
        return 0;
    }

    @Override
    public int delete(DataSource dataSource, Department o) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(DataSource dataSource, int id) throws SQLException {
        return 0;
    }

    @Override
    public List<Department> getAll(DataSource dataSource) throws SQLException {
        String sql = "SELECT * FROM department";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            departments.clear();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                departments.add(Converter.resultSetToDepartment(resultSet));
            }
            return departments;
        }
    }

    @Override
    public Department findById(DataSource dataSource, int id) throws SQLException {
        String sql = "SELECT * FROM department where department_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return Converter.resultSetToDepartment(resultSet);
        }
    }
    @Override
    public int update(int id, DataSource dataSource, Department o) throws SQLException {
        return 0;
    }
}
