package com.hrant.repository;

import com.hrant.model.Position;
import com.hrant.util.ResultSetConverter;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository implements Repository<Position> {

    private static List<Position> positions = new ArrayList<>();

    @Override
    public int insert(DataSource dataSource, Position position) throws SQLException {
        return 0;
    }

    @Override
    public int delete(DataSource dataSource, Position position) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(DataSource dataSource, int id) throws SQLException {
        return 0;
    }

    @Override
    public Position findById(DataSource dataSource, int id) throws SQLException {
        String sql = "SELECT * FROM positions where position_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return ResultSetConverter.resultSetToPosition(resultSet);
        }
    }

    @Override
    public int update(int id, DataSource dataSource, Position position) throws SQLException {
        return 0;
    }

    @Override
    public List<Position> getAll(DataSource dataSource) throws SQLException {
        String sql = "SELECT * FROM positions";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            positions.clear();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                positions.add(ResultSetConverter.resultSetToPosition(resultSet));
            }
            return positions;
        }
    }
}
