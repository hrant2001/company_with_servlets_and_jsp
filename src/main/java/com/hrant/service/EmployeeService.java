package com.hrant.service;

import com.hrant.dto.DepartmentDto;
import com.hrant.dto.EmployeeDto;
import com.hrant.dto.PositionDto;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeService() {
        throw new AssertionError();
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employeeDto The employee to add to the list.
     */
    public static void addEmployee(EmployeeDto employeeDto) {
        try {
            if (employeeRepository.insert(dataSource, DtoConverter.dtoToEmployee(employeeDto)) == 1) {
                LOGGER.warn("The employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " is already in the list");
                System.out.println("\nThe employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " is already in the list\n");
            } else {
                LOGGER.info("The employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was successfully added to the list of employees");
                System.out.println("\nThe employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was successfully added to the list of employees\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Deletes an employee from the database.
     *
     * @param employeeDto The employee to delete from the list.
     */
    public static void deleteEmployee(EmployeeDto employeeDto) {
        try {
            if (employeeRepository.delete(dataSource, DtoConverter.dtoToEmployee(employeeDto)) == 0) {
                LOGGER.info("The employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was successfully deleted from the list of employees");
                System.out.println("\nThe employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was successfully deleted from the list of employees\n");
            } else {
                LOGGER.warn("The employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was not found in the list of employees");
                System.out.println("\nThe employee " + employeeDto.getFName() + " " + employeeDto.getLName() + " " + employeeDto.getBirthday() + " was not found in the list of employees\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void deleteEmployeeById(int id) {
        try {
            if (employeeRepository.deleteById(dataSource, id) == 0) {
                LOGGER.info("The employee with the id " + id + " was successfully deleted from the list of employees");
                System.out.println("\nThe employee with the id " + id + " was successfully deleted from the list of employees\n");
            } else {
                LOGGER.warn("The employee with the id " + id + " was not found in the list of employees");
                System.out.println("\nThe employee with the id " + id + " was not found in the list of employees\n");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void updateEmployee(int id, EmployeeDto employeeDto) {
        try {
            employeeRepository.update(id, dataSource, DtoConverter.dtoToEmployee(employeeDto));
            LOGGER.info("The employee with the id " + id + " was successfully updated from the list of employees");
            System.out.println("\nThe employee with the id " + id + " was successfully updated from the list of employees\n");
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
        List<Employee> employees = new ArrayList<>();
        try {
            employees = EmployeeRepository.findByLastName(dataSource, lastName);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees.isEmpty()) {
            LOGGER.warn("The employee with the last name of " + lastName + " was not found in the list");
            System.out.println("The employee with the last name of " + lastName + " was not found in the list");
            return;
        }
        employees.forEach(System.out::println);
    }

    public static EmployeeDto findEmployeeById(int id) {
        Employee employee;
        try {
            employee = employeeRepository.findById(dataSource, id);
            LOGGER.warn("The employee with the id of " + id + " was successfully found in the list");
            System.out.println("The employee with the id of " + id + " was successfully found in the list");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        if (employee == null) {
            LOGGER.warn("The employee with the id of " + id + " was not found in the list");
            System.out.println("The employee with the id of " + id + " was not found in the list");

            return new EmployeeDto();
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
            LOGGER.warn("The list of employees is empty");
            System.out.println("\nThe list of employees is empty\n");
            return;
        }
        System.out.println();
        employees.stream().sorted(Comparator.comparing(Employee::getLName)).forEach(System.out::println);
    }

    /**
     * Prints the list of the employees.
     */
    public static List<EmployeeDto> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeRepository.getAll(dataSource);
            LOGGER.warn("The list of employees was successfully found");
            System.out.println("The list of employees was successfully found");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees == null || employees.isEmpty()) {
            LOGGER.warn("The list of employees is empty");
            System.out.println("\nThe list of employees is empty\n");
            return Collections.emptyList();
        }
        return getEmployeeDtos(employees);
    }

    public static List<EmployeeDto> getEmployeesByCriteria(String fname, String lname, String birthday, int positionId, int departmentId) {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = EmployeeRepository.getByCriteria(dataSource, fname, lname, birthday, positionId, departmentId);
            LOGGER.warn("The list of employees with specific criteria was successfully found");
            System.out.println("The list of employees with specific criteria was successfully found");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (employees == null || employees.isEmpty()) {
            LOGGER.warn("The searched list of employees is empty");
            System.out.println("\nThe searched list of employees is empty\n");
            return Collections.emptyList();
        }
        return getEmployeeDtos(employees);
    }

    private static List<EmployeeDto> getEmployeeDtos(List<Employee> employees) {
        List<EmployeeDto> employeesDto = new ArrayList<>();
        for (Employee e : employees) {
            EmployeeDto employeeDto = DtoConverter.employeeToDto(e);
            PositionDto positionDto = PositionService.findPositionById(e.getPositionId());
            DepartmentDto departmentDto = DepartmentService.findDepartmentById(e.getDepartmentId());
            employeeDto.setPositionName(positionDto.getName());
            employeeDto.setDepartmentName(departmentDto.getName());
            employeesDto.add(employeeDto);
        }
        return employeesDto;
    }
}