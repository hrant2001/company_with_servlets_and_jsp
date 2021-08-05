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
    <form action="search-rec" method="get">
        <div style="float: left; margin-right: 20px">
            <p>Search for the entrance date</p>
            <input id="rec_date"
                   value="<%=request.getParameter("rec_date")!=null?request.getParameter("rec_date"):""%>"
                   name="rec_date" width="270">
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
            <button class="btn btn-success" onclick="clearAll()">Reset</button>
        </div>
    </form>

    <table id="myTable" class="table table-bordered table-striped">
        <thead>
        <tr>
            <th onclick="sortById()">Record Id</th>
            <th>Entrance time</th>
            <th>Exit time</th>
            <th onclick="sort()">Employee</th>
        </tr>
        </thead>
        <tbody id="tableBody">
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
    $('#rec_date').datepicker({
        uiLibrary: 'bootstrap',
        format: 'yyyy-mm-dd'
    });

    function sortById() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("myTable");
        switching = true;
        /*Make a loop that will continue until
        no switching has been done:*/
        while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.rows;
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[0];
                y = rows[i + 1].getElementsByTagName("TD")[0];
                //check if the two rows should switch place:
                if (Number(x.innerHTML) > Number(y.innerHTML)) {
                    //if so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
            if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }

    function sort() {
        var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
        table = document.getElementById("myTable");
        switching = true;
        //Set the sorting direction to ascending:
        dir = "asc";
        /*Make a loop that will continue until
        no switching has been done:*/
        while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.rows;
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[3];
                y = rows[i + 1].getElementsByTagName("TD")[3];
                /*check if the two rows should switch place,
                based on the direction, asc or desc:*/
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == "desc") {
                    if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                        //if so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
                //Each time a switch is done, increase this count by 1:
                switchcount++;
            } else {
                /*If no switching has been done AND the direction is "asc",
                set the direction to "desc" and run the while loop again.*/
                if (switchcount == 0 && dir == "asc") {
                    dir = "desc";
                    switching = true;
                }
            }
        }
    }

    function clearAll() {
        document.getElementById("rec_date").value = "";
        document.getElementById("employee_name").value = "";
    }
</script>
</body>
</html>