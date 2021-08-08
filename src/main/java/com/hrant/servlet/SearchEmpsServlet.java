package com.hrant.servlet;

import com.hrant.dto.EmployeeDto;
import com.hrant.service.DepartmentService;
import com.hrant.service.EmployeeService;
import com.hrant.service.PositionService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.hrant.util.ServletUtil.DEPARTMENT_ATTR;
import static com.hrant.util.ServletUtil.POSITION_ATTR;
import static com.hrant.util.ServletUtil.getDepartmentsAsString;
import static com.hrant.util.ServletUtil.getPositionsAsString;

@WebServlet(name = "SearchEmpsServlet", value = "/search-emp")
public class SearchEmpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String birthday = request.getParameter("birthday");
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        List<EmployeeDto> employeesDto = EmployeeService.getEmployeesByCriteria(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department));
        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());

        request.setAttribute("employees", employeesDto);
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
