package com.hrant.service;

import com.hrant.model.AttendanceRecord;
import com.hrant.model.Position;
import com.hrant.repository.AttendanceRecordRepository;
import com.hrant.repository.PositionRepository;
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

public class AttendanceRecordService {
    private static Repository<AttendanceRecord> recordRepository = new AttendanceRecordRepository();

    private static DataSource dataSource;

    static {
        dataSource = DataSourceFactory.getInstance();
    }

    /**
     * The resource bundle
     */
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale("en"));

    private final static Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

    public static void findRecordById(int id) {
        AttendanceRecord record = null;
        try {
            record = recordRepository.findById(dataSource, id);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (record == null) {
            LOGGER.warn("The record with the id of " + id + " was not found in the list");
            System.out.println(resourceBundle.getString("emp") + " " + resourceBundle.getString("not.found") + " " + id + " "
                    + resourceBundle.getString("not.in.list"));
        }
    }

    public static List<AttendanceRecord> getPositions() {
        List<AttendanceRecord> records = null;
        try {
            records = recordRepository.getAll(dataSource);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        if (records == null || records.isEmpty()) {
            System.out.println("\n" + resourceBundle.getString("empty.list") + "\n");
            LOGGER.warn("The list of employees is empty");
            return new ArrayList<>();
        }
        return records;
    }
}
