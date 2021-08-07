package com.hrant.repository;

import com.hrant.model.Employee;
import com.hrant.util.ResultSetConverter;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EmployeeRepository implements Repository<Employee> {

    public int insert(DataSource dataSource, Employee employee) throws SQLException {
        String sql = "INSERT INTO employee(fname,lname,birthday,position_id,department_id) VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            List<Employee> employees = getAll(dataSource);
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
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee WHERE lname=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, lastName);
            //use try-with-resources to close ResultSet
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
                }
            }

            return employees;
        }
    }

    public Employee findById(DataSource dataSource, int id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            //use try-with-resources to close ResultSet
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                resultSet.next();

                return ResultSetConverter.resultSetToEmployee(resultSet);
            }
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
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Statement statement = dataSource.getConnection().createStatement()) {

            //use try-with-resources to close ResultSet
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
                }
            }
            return employees;
        }
    }

    public static List<Employee> getByCriteria(DataSource dataSource, String fname, String lname, String birthday, int positionId, int departmentId) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM EMPLOYEE WHERE fname LIKE ? AND lname LIKE ? AND birthday LIKE ?");
        List<Employee> employees = new ArrayList<>();
        boolean searchWithPosId = false;
        boolean searchWithDepId = false;

        if (positionId >= 0) {
            queryBuilder.append(" AND position_id=?");
            searchWithPosId = true;
        }
        if (departmentId >= 0) {
            queryBuilder.append(" AND department_id=?");
            searchWithDepId = true;
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            preparedStatement.setString(1, '%' + fname + '%');
            preparedStatement.setString(2, '%' + lname + '%');
            preparedStatement.setString(3, '%' + birthday + '%');
            if (searchWithPosId) {
                preparedStatement.setInt(4, positionId);
            }
            if (searchWithDepId) {
                if (searchWithPosId) {
                    preparedStatement.setInt(5, departmentId);
                } else {
                    preparedStatement.setInt(4, departmentId);
                }
            }

            //use try-with-resources to close ResultSet
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    employees.add(ResultSetConverter.resultSetToEmployee(resultSet));
                }
            }
            return employees;
        }
    }
}

