package com.hrant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceRecord {
    private int recordId;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private int employeeId;

    public AttendanceRecord() {
    }

    public AttendanceRecord(int recordId, LocalDateTime entranceTime, LocalDateTime exitTime, int employeeId) {
        this.recordId = recordId;
        this.entranceTime = entranceTime;
        this.exitTime = exitTime;
        this.employeeId = employeeId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public LocalDateTime getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(LocalDateTime entranceTime) {
        this.entranceTime = entranceTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return recordId + " " + entranceTime + " " + exitTime + " " + employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return recordId == that.recordId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId);
    }
}
