package com.hrant.servlet;

import com.hrant.dto.AttendanceRecordDto;
import com.hrant.dto.EmployeeDto;
import com.hrant.service.AttendanceRecordService;
import com.hrant.service.EmployeeService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AttendanceRecordsServlet", value = {"/attendance-records", "/search-rec"})
public class AttendanceRecordsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/attendance-records":
                listRecords(request, response);
                break;
            case "/search-rec":
                searchRecords(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static EmployeeDto findEmployeeById(int id) {
        return EmployeeService.findEmployeeById(id);
    }

    private void listRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AttendanceRecordDto> attendance_records = AttendanceRecordService.getRecords();
        request.setAttribute("attendance_records", attendance_records);
        request.getRequestDispatcher("/attendance_records.jsp").forward(request, response);
    }

    private void searchRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String record_date = request.getParameter("rec_date");
        String full_name = request.getParameter("employee_name");

        List<AttendanceRecordDto> attendance_records = AttendanceRecordService.getRecordsByCriteria(record_date, full_name);
        request.setAttribute("attendance_records", attendance_records);

        request.getRequestDispatcher("/attendance_records.jsp").forward(request, response);
    }
}
