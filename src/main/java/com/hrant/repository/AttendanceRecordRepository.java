package com.hrant.repository;

import com.hrant.model.AttendanceRecord;
import com.hrant.util.ResultSetConverter;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordRepository implements Repository<AttendanceRecord> {

    private static List<AttendanceRecord> records = new ArrayList<>();

    @Override
    public int insert(DataSource dataSource, AttendanceRecord attendanceRecord) throws SQLException {
        return 0;
    }

    @Override
    public int delete(DataSource dataSource, AttendanceRecord attendanceRecord) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(DataSource dataSource, int id) throws SQLException {
        return 0;
    }

    @Override
    public List<AttendanceRecord> getAll(DataSource dataSource) throws SQLException {
        String sql = "SELECT * FROM attendance_record";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            records.clear();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                records.add(ResultSetConverter.resultSetToRecord(resultSet));
            }
            return records;
        }
    }

    @Override
    public AttendanceRecord findById(DataSource dataSource, int id) throws SQLException {
        String sql = "SELECT * FROM attendance_record where record_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return ResultSetConverter.resultSetToRecord(resultSet);
        }
    }

    @Override
    public int update(int id, DataSource dataSource, AttendanceRecord attendanceRecord) throws SQLException {
        return 0;
    }
}
