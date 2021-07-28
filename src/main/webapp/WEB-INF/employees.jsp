<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <p>Search the table for first names, last names, birthdays, positions or
        departments:</p>
    <input class="form-control" id="myInput" type="text" placeholder="Search..">
    <br>
    <table class="table table-bordered table-striped">
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
        <tbody id="myTable">
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.getEmployeeId()}</td>
                <td>${emp.getFName()}</td>
                <td>${emp.getLName()}</td>
                <td>${emp.getBirthday()}</td>
                <td>${emp.getPositionId()}</td>
                <td>${emp.getDepartmentId()}</td>
                <td><a href="edit-emp?id=${emp.getEmployeeId()}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete-emp?id=${emp.getEmployeeId()}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="<%=request.getContextPath()%>/new-emp" class="btn btn-success">Add employee</a>
</div>

<script>
    $(document).ready(function () {
        $("#myInput").on("keyup", function () {
            var value = $(this).val().toLowerCase();
            $("#myTable tr").filter(function () {
                $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            });
        });
    });
</script>
</body>
</html>