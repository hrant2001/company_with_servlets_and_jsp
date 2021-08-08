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

import static com.hrant.util.ServletUtil.*;

@WebServlet(name = "EditEmpServlet", value = "/edit-emp")
public class EditEmpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        EmployeeDto existingEmployee = EmployeeService.findEmployeeById(id);
        List<EmployeeDto> employeesDto = EmployeeService.getEmployees();
        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());

        request.setAttribute("employee", existingEmployee);
        request.setAttribute("employees", employeesDto);
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);

        request.getRequestDispatcher("edit_employee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
