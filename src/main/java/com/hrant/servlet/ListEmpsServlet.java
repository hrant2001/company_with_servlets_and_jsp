package com.hrant.servlet;

import com.hrant.dto.EmployeeDto;
import com.hrant.service.DepartmentService;
import com.hrant.service.EmployeeService;
import com.hrant.service.PositionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.hrant.util.ServletUtil.*;

@WebServlet(name = "EmployeesServlet", value = "/")
public class ListEmpsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EmployeeDto> employeesDto = EmployeeService.getEmployees();

        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);
        request.setAttribute("employees", employeesDto);

        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
