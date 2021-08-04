package com.hrant.repository;

import com.hrant.dto.EmployeeDto;
import com.hrant.model.Employee;
import com.hrant.util.ResultSetConverter;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EmployeeRepository implements Repository<Employee> {

    /**
     * The list of the employees
     */
    private static List<Employee> employees = new ArrayList<>();

    public int insert(DataSource dataSource, Employee employee) throws SQLException {
        String sql = "INSERT INTO employee(fname,lname,birthday,position_id,department_id) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            employees = getAll(dataSource);
            if (employees.contains(employee)) {
                return 1;
            }
            preparedStatement.setString(1, employee.getFName());
            preparedStatement.setString(2, employee.getLName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            preparedStatement.setInt(4, employee.getPositionId());
            preparedStatement.setInt(5, employee.getDepartmentId());

            if (preparedStatement.executeUpdate() > 0)
                return 0; //ok

            return 1; //not ok
        }
    }

    public int delete(DataSource dataSource, Employee employee) throws SQLException {
        String sql = "DELETE FROM employee WHERE fname=? AND lname=? AND birthday=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getFName());
            preparedStatement.setString(2, employee.getLName());
            preparedStatement.setDate(3, Date.valueOf(employee.getBirthday()));
            if (preparedStatement.executeUpdate() > 0)
                return 0;
            return 1;
        }
    }

    @Override
    public int deleteById(DataSource dataSource, int id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0)
                return 0;
            return 1;
        }
    }

    public static List<Employee> findByLastName(DataSource dataSource, String lastName) throws SQLException {
        String sql = "SELECT * FROM employee WHERE lname=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            employees.clear();
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
            }

            return employees;
        }
    }

    public Employee findById(DataSource dataSource, int id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return ResultSetConverter.resultSetToEmployee(resultSet);
        }
    }

    @Override
    public int update(int id, DataSource dataSource, Employee employee) throws SQLException {
        String sql = "UPDATE employee SET fname=?, lname=?,birthday=?,position_id=?,department_id=? WHERE employee_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getFName());
            preparedStatement.setString(2, employee.getLName());
            preparedStatement.setString(3, employee.getBirthday().toString());
            preparedStatement.setInt(4, employee.getPositionId());
            preparedStatement.setInt(5, employee.getDepartmentId());
            preparedStatement.setInt(6, id);

            return preparedStatement.executeUpdate();
        }
    }

    public List<Employee> getAll(DataSource dataSource) throws SQLException {
        String sql = "SELECT * FROM employee";
        try (Statement statement = dataSource.getConnection().createStatement()) {
            employees.clear();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
            }
            return employees;
        }
    }

    public static List<Employee> getByCriteria(DataSource dataSource, String fname, String lname, String birthday, String positionId, String departmentId) throws SQLException {
        String sql = "select * from employee where fname like concat('%',?,'%') and lname like concat('%',?,'%') and birthday like concat('%',?,'%') and position_id like concat('%',?,'%') and department_id like concat('%',?,'%')";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, lname);
            preparedStatement.setString(3, birthday);
            preparedStatement.setString(4, positionId);
            preparedStatement.setString(5, departmentId);

            employees.clear();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
            }

            return employees;
        }
    }
}

