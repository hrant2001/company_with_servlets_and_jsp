package com.hrant.util;

import com.hrant.model.AttendanceRecord;
import com.hrant.model.Department;
import com.hrant.model.Employee;
import com.hrant.model.Position;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ResultSetConverter {
    public static Employee resultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setFName(resultSet.getString("fname"));
        employee.setLName(resultSet.getString("lname"));
        employee.setBirthday(resultSet.getDate("birthday").toLocalDate());
        employee.setPositionId(resultSet.getInt("position_id"));
        employee.setDepartmentId(resultSet.getInt("department_id"));

        return employee;
    }

    public static Position resultSetToPosition(ResultSet resultSet) throws SQLException {
        Position position = new Position();

        position.setPositionId(resultSet.getInt("position_id"));
        position.setName(resultSet.getString("position_name"));
        position.setShortName(resultSet.getString("position_short_name"));

        return position;
    }

    public static Department resultSetToDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();

        department.setDepartmentId(resultSet.getInt("department_id"));
        department.setName(resultSet.getString("department_name"));

        return department;
    }

    public static AttendanceRecord resultSetToRecord(ResultSet resultSet) throws SQLException {
        AttendanceRecord record = new AttendanceRecord();

        record.setRecordId(resultSet.getInt("record_id"));
        record.setEntranceTime(resultSet.getTimestamp("entrance_time").toLocalDateTime());
        record.setExitTime(resultSet.getTimestamp("exit_time").toLocalDateTime());
        record.setEmployeeId(resultSet.getInt("employee_id"));

        return record;
    }
}

