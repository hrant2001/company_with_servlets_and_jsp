package com.hrant.servlet;

import com.hrant.dto.EmployeeDto;
import com.hrant.dto.PositionDto;
import com.hrant.dto.DepartmentDto;
import com.hrant.service.DepartmentService;
import com.hrant.service.EmployeeService;
import com.hrant.service.PositionService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "EmployeesServlet", value = "/")
public class EmployeesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/new-emp":
                showAddedRow(request, response);
                break;
            case "/add-emp":
                insertEmployee(request, response);
                break;
            case "/edit-emp":
                showEditingRow(request, response);
                break;
            case "/update-emp":
                updateEmployee(request, response);
                break;
            case "/delete-emp":
                deleteEmployee(request, response);
                break;
            case "/search-emp":
                searchEmployees(request, response);
                break;
            default:
                listEmployees(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void showAddedRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EmployeeDto> employeesDto = EmployeeService.getEmployees();
        List<PositionDto> positionsDto = PositionService.getPositions();
        List<DepartmentDto> departmentsDto = DepartmentService.getDepartments();

        request.setAttribute("employees", employeesDto);
        request.setAttribute("positions", positionsDto);
        request.setAttribute("departments", departmentsDto);
        request.getRequestDispatcher("add_employee.jsp").forward(request, response);
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        EmployeeService.addEmployee(new EmployeeDto(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department)));

        response.sendRedirect("employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EmployeeService.deleteEmployeeById(id);

        response.sendRedirect("employees");
    }

    private void searchEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String birthday = request.getParameter("birthday");
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        List<EmployeeDto> employeesDto = EmployeeService.getEmployeesByEverything(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department));
        List<PositionDto> positionsDto = PositionService.getPositions();
        List<DepartmentDto> departmentsDto = DepartmentService.getDepartments();

        request.setAttribute("employees", employeesDto);
        request.setAttribute("positions", positionsDto);
        request.setAttribute("departments", departmentsDto);
        request.getRequestDispatcher("employees.jsp").forward(request, response);

    }

    private void showEditingRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        EmployeeDto existingEmployee = EmployeeService.findEmployeeById(id);
        List<EmployeeDto> employeesDto = EmployeeService.getEmployees();
        List<PositionDto> positionsDto = PositionService.getPositions();
        List<DepartmentDto> departmentsDto = DepartmentService.getDepartments();

        request.setAttribute("employee", existingEmployee);
        request.setAttribute("employees", employeesDto);
        request.setAttribute("positions", positionsDto);
        request.setAttribute("departments", departmentsDto);

        request.getRequestDispatcher("edit_employee.jsp").forward(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        EmployeeService.updateEmployee(id, new EmployeeDto(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department)));

        response.sendRedirect("employees");
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EmployeeDto> employeesDto = EmployeeService.getEmployees();
        List<PositionDto> positionsDto = PositionService.getPositions();
        List<DepartmentDto> departmentsDto = DepartmentService.getDepartments();

        request.setAttribute("employees", employeesDto);
        request.setAttribute("positions", positionsDto);
        request.setAttribute("departments", departmentsDto);

        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }

    public static PositionDto findPositionById(int id) {
        return PositionService.findPositionById(id);
    }

    public static DepartmentDto findDepartmentById(int id) {
        return DepartmentService.findDepartmentById(id);
    }

    public static List<PositionDto> findPositions() {
        return PositionService.getPositions();
    }

    public static List<DepartmentDto> findDepartments() {
        return DepartmentService.getDepartments();
    }
}
