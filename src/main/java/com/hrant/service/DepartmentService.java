package com.hrant.service;

import com.hrant.model.Department;
import com.hrant.repository.DepartmentRepository;
import com.hrant.repository.Repository;
import com.hrant.util.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DepartmentService {

    private static Repository<Department> departmentRepository = new DepartmentRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale("en"));

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    public static Department findDepartmentById(int id) {
        Department department = null;
        try {
            department = departmentRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (department == null) {
            LOGGER.warn("The department with the id of " + id + " was not found in the list");
            System.out.println(resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + id + " "
                    + resourceBundle.getString("not.in.list"));
        }

        return department;
    }

    public static List<Department> getDepartments() {
        List<Department> departments = null;
        try {
            departments = departmentRepository.getAll(dataSource);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (departments == null || departments.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return new ArrayList<>();
        }
        return departments;
    }
}
