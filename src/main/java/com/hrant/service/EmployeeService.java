package com.hrant.service;

import com.hrant.dto.EmployeeDto;
import com.hrant.model.Employee;
import com.hrant.repository.EmployeeRepository;
import com.hrant.repository.Repository;
import com.hrant.util.DataSourceFactory;
import com.hrant.util.DtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class EmployeeService {

    private static Repository<Employee> employeeRepository = new EmployeeRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    /**
     * The resource bundle
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale("en"));

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * Adds a new employee to the database.
     *
     * @param employee The employee to add to the list.
     */
    public static void addEmployee(Employee employee) {
        try {
            if (employeeRepository.insert(dataSource, employee) == 1) {
                LOGGER.warn("The employee " + employee.getFName() + " " + employee.getLName() + " " + employee.getBirthday() + " is already in the list");
                System.out.println("\n" + resourceBundle.getString("emp") + employee.getFName() + " " + employee.getLName() + " " + employee.getBirthday() + " " + resourceBundle.getString("in-list") + "\n");
            } else {
                LOGGER.info("The employee " + employee.getFName() + " "
                        + employee.getLName() + " " + employee.getBirthday() + " was successfully added to the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFName() + " "
                        + employee.getLName() + " " + employee.getBirthday() + " " + resourceBundle.getString("success.add") + "\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Deletes an employee from the database.
     *
     * @param employee The employee to delete from the list.
     */
    public static void deleteEmployee(Employee employee) {
        try {
            if (employeeRepository.delete(dataSource, employee) == 0) {
                LOGGER.info("The employee " + employee.getFName() + " " + employee.getLName() +
                        " " + employee.getBirthday() + " was successfully deleted from the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFName() + " " + employee.getLName() +
                        " " + employee.getBirthday() + " " + resourceBundle.getString("success.delete") + "\n");
            } else {
                LOGGER.warn("The employee " + employee.getFName() + " " + employee.getLName() + " " + employee.getBirthday() + " was not found in the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " " + employee.getFName() + " " + employee.getLName() + " " + employee.getBirthday() + " " + resourceBundle.getString("not.in.list") + "\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void deleteEmployeeById(int id) {
        try {
            if (employeeRepository.deleteById(dataSource, id) == 0) {
                LOGGER.info("The employee with the id " + id + " was successfully deleted from the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " with the id " + id + " " + resourceBundle.getString("success.delete") + "\n");
            } else {
                LOGGER.warn("The employee with the id " + id + " was not found in the list of employees");
                System.out.println("\n" + resourceBundle.getString("emp") + " with the id" + id + " " + resourceBundle.getString("not.in.list") + "\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void updateEmployee(int id, Employee employee) {
        try {
            employeeRepository.update(id, dataSource, employee);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Finds the employees by their last name in the database.
     *
     * @param lastName The last name of the employees to find by in the list.
     */
    public static void findEmployeeByLastName(String lastName) {
        List<Employee> employees = null;
        try {
            employees = EmployeeRepository.findByLastName(dataSource, lastName);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees.isEmpty()) {
            LOGGER.warn("The employee with the last name of " + lastName + " was not found in the list");
            System.out.println(resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + lastName + " "
                    + resourceBundle.getString("not.in.list"));
            return;
        }
        employees.forEach(System.out::println);
    }

    public static EmployeeDto findEmployeeById(int id) {
        Employee employee;
        try {
            employee = employeeRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        if (employee == null) {
            LOGGER.warn("The employee with the id of " + id + " was not found in the list");
            System.out.println(resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + id + " "
                    + resourceBundle.getString("not.in.list"));
        }

        return DtoConverter.employeeToDto(employee);
    }

    /**
     * Prints the sorted list of the employees by the last name.
     */
    public static void sortListOfEmployees() {
        List<Employee> employees = null;
        try {
            employees = employeeRepository.getAll(dataSource);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return;
        }
        System.out.println();
        employees.stream().sorted(Comparator.comparing(Employee::getLName)).forEach(System.out::println);
    }

    /**
     * Prints the list of the employees.
     */
    public static List<EmployeeDto> getEmployees() {
        List<Employee> employees = null;
        try {
            employees = employeeRepository.getAll(dataSource);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees == null || employees.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return new ArrayList<>();
        }
        List<EmployeeDto> employeesDto = new ArrayList<>();
        for (Employee e : employees) {
            employeesDto.add(DtoConverter.employeeToDto(e));
        }

        return employeesDto;
    }
}