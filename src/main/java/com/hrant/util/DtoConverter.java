package com.hrant.util;

import com.hrant.dto.AttendanceRecordDto;
import com.hrant.dto.DepartmentDto;
import com.hrant.dto.EmployeeDto;
import com.hrant.dto.PositionDto;
import com.hrant.model.AttendanceRecord;
import com.hrant.model.Department;
import com.hrant.model.Employee;
import com.hrant.model.Position;

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

    public static PositionDto positionToDto(Position position) {
        PositionDto positionDto = new PositionDto();

        positionDto.setPositionId(position.getPositionId());
        positionDto.setName(position.getName());
        positionDto.setShortName(position.getShortName());

        return positionDto;
    }

    public static DepartmentDto departmentToDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setDepartmentId(department.getDepartmentId());
        departmentDto.setName(department.getName());

        return departmentDto;
    }

    public static AttendanceRecordDto attendanceRecordToDto(AttendanceRecord record) {
        AttendanceRecordDto recordDto = new AttendanceRecordDto();

        recordDto.setRecordId(record.getRecordId());
        recordDto.setEntranceTime(record.getEntranceTime());
        recordDto.setExitTime(record.getExitTime());
        recordDto.setEmployeeId(record.getEmployeeId());

        return recordDto;
    }
}
