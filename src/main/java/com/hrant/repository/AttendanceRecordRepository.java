package com.hrant.repository;

import com.hrant.model.AttendanceRecord;
import com.hrant.util.ResultSetConverter;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordRepository implements Repository<AttendanceRecord> {

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
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM attendance_record";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    records.add(ResultSetConverter.resultSetToRecord(resultSet));
                }
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

    public static List<AttendanceRecord> getByCriteria(DataSource dataSource, String record_date, String full_name) throws SQLException {
        List<AttendanceRecord> records = new ArrayList<>();
        String sql = "select attendance_record.record_id,attendance_record.entrance_time,attendance_record.exit_time,employee.employee_id " +
                "from attendance_record inner join employee on attendance_record.employee_id=employee.employee_id " +
                "where entrance_time like concat('%',?,'%') and concat(employee.fname,' ',employee.lname) like concat('%',?,'%')";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, record_date);
            preparedStatement.setString(2, full_name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                records.add(ResultSetConverter.resultSetToRecord(resultSet));
            }
            return records;
        }
    }
}
