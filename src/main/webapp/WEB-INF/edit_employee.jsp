<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hrant.service.DepartmentService" %>
<%@ page import="com.hrant.service.PositionService" %>
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
<form action="update-emp?id=${employee.getEmployeeId()}" method="post">
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
                    <c:if test="${emp.getEmployeeId() == employee.getEmployeeId()}">
                        <td>
                            <label>
                                <input type="text" value="<c:out value='${emp.getFName()}' />" name="fname"
                                       required="required">
                            </label>
                        </td>
                        <td>
                            <label>
                                <input type="text" value="<c:out value='${emp.getLName()}' />" name="lname"
                                       required="required">
                            </label>
                        </td>
                        <td>
                            <label>
                                <input type="text" value="<c:out value='${emp.getBirthday()}' />" name="birthday"
                                       required="required">
                            </label></td>
                        <td>
                            <label>
                                <input type="text" value="<c:out value='${PositionService.findPositionById(emp.getPositionId()).name}' />" name="position"
                                       required="required">
                            </label></td>
                        <td>
                            <label>
                                <input type="text" value="<c:out value='${DepartmentService.findDepartmentById(emp.getDepartmentId()).name}' />" name="department"
                                       required="required">
                            </label></td>
                        <td>
                            <button type="submit" class="btn btn-success">Save</button>
                        </td>
                    </c:if>
                    <c:if test="${emp.getEmployeeId() != employee.getEmployeeId()}">
                        <td>${emp.getFName()}</td>
                        <td>${emp.getLName()}</td>
                        <td>${emp.getBirthday()}</td>
                        <td>${emp.getPositionId()}</td>
                        <td>${emp.getDepartmentId()}</td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
</form>
</body>
</html>