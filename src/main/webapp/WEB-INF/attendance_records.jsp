<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>The list of the attendance records</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <h3>Attendance records</h3>
    <ul class="nav nav-tabs">
        <li><a href="employees">Employees</a></li>
        <li class="active"><a href="attendance-records">Attendance records</a></li>
    </ul>
    <br>
</div>

<div class="container">
    <p>Search the table for enter time, exit time or employee full name:</p>
    <input class="form-control" id="myInput" type="text" placeholder="Search..">
    <br>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Record Id</th>
            <th>Entrance time</th>
            <th>Exit time</th>
            <th>Employee</th>
        </tr>
        </thead>
        <tbody id="myTable">
        <c:forEach var="rec" items="${attendance_records}">
            <tr>
                <td>${rec.getRecordId()}</td>
                <td>${rec.getEntranceTime()}</td>
                <td>${rec.getExitTime()}</td>
                <td>${rec.getEmployeeId()}</td>
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

</body>
</html>