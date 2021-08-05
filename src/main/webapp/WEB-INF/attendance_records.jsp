<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hrant.servlet.AttendanceRecordsServlet" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>The list of the attendance records</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css"/>
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
    <form action="attendance-records/search-rec" method="post">
        <div style="float: left; margin-right: 20px">
            <p>Search for the entrance date</p>
            <input id="rec-date"
                   value="<%=request.getParameter("rec-date")!=null?request.getParameter("rec-date"):""%>"
                   name="rec-date" width="270">
        </div>

        <div style="float: left">
            <p>Search for the employee full name</p>
            <input class="form-control" id="employee_name"
                   value="<%=request.getParameter("employee_name")!=null?request.getParameter("employee_name"):""%>"
                   name="employee_name" type="search" placeholder="Full Name...">
        </div>
        <br>
        <br>
        <br>
        <br>
        <div>
            <button type="submit" class="btn btn-success">Search</button>
        </div>
    </form>

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
                <td>${AttendanceRecordsServlet.findEmployeeById(rec.getEmployeeId()).FName} ${AttendanceRecordsServlet.findEmployeeById(rec.getEmployeeId()).LName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $('#rec-date').datepicker({
        uiLibrary: 'bootstrap',
        format: 'yyyy-mm-dd'
    });
</script>
</body>
</html>