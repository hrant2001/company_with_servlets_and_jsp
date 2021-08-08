package com.hrant.service;

import com.hrant.dto.AttendanceRecordDto;
import com.hrant.dto.DepartmentDto;
import com.hrant.dto.EmployeeDto;
import com.hrant.dto.PositionDto;
import com.hrant.model.AttendanceRecord;
import com.hrant.repository.AttendanceRecordRepository;
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

public class AttendanceRecordService {
    private static Repository<AttendanceRecord> recordRepository = new AttendanceRecordRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

    private AttendanceRecordService() {
        throw new AssertionError();
    }

    public static AttendanceRecordDto findRecordById(int id) {
        AttendanceRecord record = null;
        try {
            record = recordRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (record == null) {
            LOGGER.warn("The record with the id of " + id + " was not found in the list");
            System.out.println("The record with the id of " + id + " was not found in the list");

            return new AttendanceRecordDto();
        }

        return DtoConverter.attendanceRecordToDto(record);
    }

    public static List<AttendanceRecordDto> getRecords() {
        List<AttendanceRecord> records = new ArrayList<>();
        try {
            records = recordRepository.getAll(dataSource);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (records == null || records.isEmpty()) {
            System.out.println("\nThe list of the records is empty\n");
            LOGGER.warn("The list of the records is empty");
            return Collections.emptyList();
        }

        return getRecordDtos(records);
    }

    public static List<AttendanceRecordDto> getRecordsByCriteria(String record_date, String full_name) {
        List<AttendanceRecord> records = null;
        try {
            records = AttendanceRecordRepository.getByCriteria(dataSource, record_date, full_name);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (records == null || records.isEmpty()) {
            System.out.println("\nThe searching list of records is empty\n");
            LOGGER.warn("The searching list of records is empty");
            return new ArrayList<>();
        }

        return getRecordDtos(records);
    }

    private static List<AttendanceRecordDto> getRecordDtos(List<AttendanceRecord> records) {
        List<AttendanceRecordDto> recordDtos = new ArrayList<>();
        for (AttendanceRecord r : records) {
            AttendanceRecordDto recordDto = DtoConverter.attendanceRecordToDto(r);
            EmployeeDto employeeDto = EmployeeService.findEmployeeById(r.getEmployeeId());
            recordDto.setEmployeeFullName(employeeDto.getFName() + " " + employeeDto.getLName());
            recordDtos.add(recordDto);
        }

        return recordDtos;
    }
}
