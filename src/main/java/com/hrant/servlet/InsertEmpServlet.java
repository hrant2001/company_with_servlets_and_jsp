package com.hrant.servlet;

import com.hrant.dto.EmployeeDto;
import com.hrant.service.DepartmentService;
import com.hrant.service.EmployeeService;
import com.hrant.service.PositionService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "InsertEmpServlet", value = "/add-emp")
public class InsertEmpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        EmployeeService.addEmployee(new EmployeeDto(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department)));

        response.sendRedirect("employees");
    }
}
