package com.hrant.servlet;

import com.hrant.dto.DepartmentDto;
import com.hrant.dto.EmployeeDto;
import com.hrant.dto.PositionDto;
import com.hrant.service.DepartmentService;
import com.hrant.service.EmployeeService;
import com.hrant.service.PositionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "EmployeesServlet", value = "/")
public class EmployeesServlet extends HttpServlet {

    private static final String POSITION_ATTR = "positions";
    private static final String DEPARTMENT_ATTR = "departments";

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
        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());

        request.setAttribute("employees", employeesDto);
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);
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

        List<EmployeeDto> employeesDto = EmployeeService.getEmployeesByCriteria(fname, lname, birthday,
                PositionService.findPositionIdByName(position), DepartmentService.findDepartmentIdByName(department));
        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());

        request.setAttribute("employees", employeesDto);
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }

    private void showEditingRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String positionsAsString = getPositionsAsString(PositionService.getPositions());
        String departmentsAsString = getDepartmentsAsString(DepartmentService.getDepartments());
        request.setAttribute(POSITION_ATTR, positionsAsString);
        request.setAttribute(DEPARTMENT_ATTR, departmentsAsString);
        request.setAttribute("employees", employeesDto);

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

    private String getPositionsAsString(List<PositionDto> positions) {
        List<String> positionsAsString = positions.stream().map(PositionDto::getName).collect(Collectors.toList());
        return StringUtils.join(positionsAsString, ",");
    }

    private String getDepartmentsAsString(List<DepartmentDto> departments) {
        List<String> departmentsAsString = departments.stream().map(DepartmentDto::getName).collect(Collectors.toList());
        return StringUtils.join(departmentsAsString, ",");
    }
}
