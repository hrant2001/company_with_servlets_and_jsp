package com.hrant.service;

import com.hrant.dto.DepartmentDto;
import com.hrant.model.Department;
import com.hrant.repository.DepartmentRepository;
import com.hrant.repository.Repository;
import com.hrant.util.DataSourceFactory;
import com.hrant.util.DtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DepartmentService {

    private static Repository<Department> departmentRepository = new DepartmentRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    private DepartmentService() {
        throw new AssertionError();
    }

    public static DepartmentDto findDepartmentById(int id) {
        Department department = null;
        try {
            department = departmentRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (department == null) {
            LOGGER.warn("The department with the id of " + id + " was not found in the list");
            System.out.println("The department with the id of " + id + " was not found in the list");

            return new DepartmentDto();
        }

        return DtoConverter.departmentToDto(department);
    }

    public static List<DepartmentDto> getDepartments() {
        List<Department> departments = new ArrayList<>();
        try {
            departments = departmentRepository.getAll(dataSource);
            LOGGER.warn("The list of departments was successfully found");
            System.out.println("The list of departments was successfully found");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (departments == null || departments.isEmpty()) {
            System.out.println("\nThe list of departments is empty\n");
            LOGGER.warn("The list of departments is empty");
            return Collections.emptyList();
        }
        List<DepartmentDto> departmentsDto = new ArrayList<>();
        for (Department d : departments) {
            departmentsDto.add(DtoConverter.departmentToDto(d));
        }

        return departmentsDto;
    }

    public static int findDepartmentIdByName(String name) {
        DepartmentDto departmentDto = getDepartments().stream().filter(d -> d.getName().equalsIgnoreCase(name)).findAny().orElse(null);
        return Objects.nonNull(departmentDto) ? departmentDto.getDepartmentId() : -1;
    }
}
