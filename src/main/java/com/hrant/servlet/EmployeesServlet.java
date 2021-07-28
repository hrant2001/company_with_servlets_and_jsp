package com.hrant.servlet;

import com.hrant.model.Employee;
import com.hrant.service.EmployeeService;
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
        List<Employee> employees = EmployeeService.getEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("add_employee.jsp").forward(request, response);
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        int position = Integer.parseInt(request.getParameter("position"));
        int department = Integer.parseInt(request.getParameter("department"));

        EmployeeService.addEmployee(new Employee(fname, lname, birthday, position, department));
        response.sendRedirect("employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EmployeeService.deleteEmployeeById(id);
        response.sendRedirect("employees");
    }

    private void showEditingRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Employee existingEmployee = EmployeeService.findEmployeeById(id);
        List<Employee> employees = EmployeeService.getEmployees();
        request.setAttribute("employees", employees);
        request.setAttribute("employee", existingEmployee);
        request.getRequestDispatcher("edit_employee.jsp").forward(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        int position = Integer.parseInt(request.getParameter("position"));
        int department = Integer.parseInt(request.getParameter("department"));

        EmployeeService.updateEmployee(id, new Employee(fname, lname, birthday, position, department));
        response.sendRedirect("employees");
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = EmployeeService.getEmployees();
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("employees.jsp").forward(request, response);
    }
}
