package com.hrant.servlet;

import com.hrant.model.AttendanceRecord;
import com.hrant.service.AttendanceRecordService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AttendanceRecordsServlet", value = "/attendance-records")
public class AttendanceRecordsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AttendanceRecord> attendance_records = AttendanceRecordService.getPositions();
        request.setAttribute("attendance_records", attendance_records);
        request.getRequestDispatcher("/attendance_records.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
