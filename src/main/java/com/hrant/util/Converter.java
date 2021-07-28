package com.hrant.util;

import com.hrant.model.AttendanceRecord;
import com.hrant.model.Employee;
import com.hrant.model.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Converter {
    public static Employee resultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setFName(resultSet.getString("fname"));
        employee.setLName(resultSet.getString("lname"));
        employee.setBirthday(resultSet.getDate("birthday").toLocalDate());

        return employee;
    }

    public static Position resultSetToPosition(ResultSet resultSet) throws SQLException {
        Position position = new Position();

        position.setPositionId(resultSet.getInt("position_id"));
        position.setName(resultSet.getString("position_name"));
        position.setShortName(resultSet.getString("position_short_name"));

        return position;
    }

    public static AttendanceRecord resultSetToRecord(ResultSet resultSet) throws SQLException {
        AttendanceRecord record = new AttendanceRecord();

        record.setRecordId((resultSet.getInt("record_id")));
        record.setEntranceTime(resultSet.getTimestamp("entrance_time").toLocalDateTime());
        record.setExitTime(resultSet.getTimestamp("exit_time").toLocalDateTime());
        record.setEmployeeId((resultSet.getInt("employee_id")));

        return record;
    }
}

