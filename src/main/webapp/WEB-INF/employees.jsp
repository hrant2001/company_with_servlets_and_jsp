<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hrant.servlet.EmployeesServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>The list of the employees</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <h3>Employees</h3>
    <ul class="nav nav-tabs">
        <li class="active"><a href="employees">Employees</a></li>
        <li><a href="attendance-records">Attendance records</a></li>
    </ul>
    <br>
</div>

<div class="container">
    <%--    <p>Search the table for first names, last names, birthdays, positions or--%>
    <%--        departments:</p>--%>
    <%--    <input class="form-control" id="myInput" type="text" placeholder="Search..">--%>
    <%--    <br>--%>
    <div style="float: left; margin-right: 20px">
        <p>Search for the employee first name</p>
        <input class="form-control" id="fname" type="text" onkeyup="filter(id, 1)" placeholder="First Name...">
        &nbsp;
    </div>

    <div style="float: left; margin-right: 20px">
        <p>Search for the employee last name</p>
        <input class="form-control" id="lname" type="text" onkeyup="filter(id, 2)" placeholder="Last Name...">
    </div>

    <div style="float: left; margin-right: 20px">
        <p>Search for the employee birthday</p>
        <input class="form-control" id="birthday" type="text" onkeyup="filter(id, 3)" placeholder="Birthday...">
    </div>

    <div style="float: left; margin-right: 20px">
        <p>Search for the position</p>
        <input class="form-control" id="position" type="text" onkeyup="filter(id, 4)" placeholder="Position...">
    </div>

    <div style="float: left">
        <p>Search for the department</p>
        <input class="form-control" id="department" type="text" onkeyup="filter(id, 5)" placeholder="Department...">
    </div>
    <table class="table table-bordered table-striped" id="myTable">
        <thead>
        <tr>
            <th>Employee Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Birthday</th>
            <th>Position</th>
            <th>Department</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody id="tableBody">
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.getEmployeeId()}</td>
                <td>${emp.getFName()}</td>
                <td>${emp.getLName()}</td>
                <td>${emp.getBirthday()}</td>
                <td>${EmployeesServlet.findPositionById(emp.getPositionId()).name}</td>
                <td>${EmployeesServlet.findDepartmentById(emp.getDepartmentId()).name}</td>
                <td><a href="edit-emp?id=${emp.getEmployeeId()}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete-emp?id=${emp.getEmployeeId()}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="<%=request.getContextPath()%>/new-emp" class="btn btn-success">Add employee</a>
</div>

<script>
    function filter(inputId, index) {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById(inputId);
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[index];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>


<%--<script>--%>
<%--    $(document).ready(function () {--%>
<%--        $("#myInput").on("keyup", function () {--%>
<%--            var value = $(this).val().toLowerCase();--%>
<%--            $("#myTable tr").filter(function () {--%>
<%--                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)--%>
<%--            });--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
</body>
</html>