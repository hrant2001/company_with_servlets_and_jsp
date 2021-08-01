package com.hrant.util;

import com.hrant.dto.EmployeeDto;
import com.hrant.model.Employee;

public class DtoConverter {

    public static EmployeeDto employeeToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setFName(employee.getFName());
        employeeDto.setLName(employee.getLName());
        employeeDto.setBirthday(employee.getBirthday());
        employeeDto.setPositionId(employee.getPositionId());
        employeeDto.setDepartmentId(employee.getDepartmentId());

        return employeeDto;
    }
}
