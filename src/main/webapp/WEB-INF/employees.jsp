<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hrant.servlet.EmployeesServlet" %>
<%@ page import="com.hrant.dto.PositionDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hrant.dto.DepartmentDto" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>The list of the employees</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

    <script>
        $(function () {
            $(function () {

                var positions = [];
                <%List<PositionDto> pos = (ArrayList<PositionDto>) request.getAttribute("positions");
            for (int i = 0; i < pos.size(); i++) {
            %>

                positions[<%=i%>] = '<%=pos.get(i).getName()%>';
                <%
                     }
                 %>

                $("#pos_autocomplete").autocomplete({
                    source: positions
                });
            });
        });
    </script>
    <script>
        $(function () {

            var departments = [];
            <%List<DepartmentDto> deps = (ArrayList<DepartmentDto>) request.getAttribute("departments");
        for (int i = 0; i < deps.size(); i++) {
        %>

            departments[<%=i%>] = '<%=deps.get(i).getName()%>';
            <%
                 }
             %>

            $("#dep-autocomplete").autocomplete({
                source: departments
            });
        });
    </script>
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
    <form action="search-emp" method="get">
        <div style="float: left; margin-right: 20px">
            <p>Search for the employee first name</p>
            <input class="form-control"
                   value="<%=request.getParameter("fname")!=null?request.getParameter("fname"):""%>" id="fname"
                   name="fname"
                   type="text" placeholder="First Name...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the employee last name</p>
            <input class="form-control"
                   value="<%=request.getParameter("lname")!=null?request.getParameter("lname"):""%>" id="lname"
                   name="lname"
                   type="text" placeholder="Last Name...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the employee birthday</p>
            <input class="form-control"
                   value="<%=request.getParameter("birthday")!=null?request.getParameter("birthday"):""%>"
                   id="birthday" name="birthday" type="text" placeholder="Birthday...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the position</p>
            <input class="form-control" id="pos_autocomplete"
                   value="<%=request.getParameter("position")!=null?request.getParameter("position"):""%>"
                   name="position" type="text" placeholder="Position...">
            &nbsp;
        </div>

        <div style="float: left">
            <p>Search for the department</p>
            <input class="form-control" id="dep-autocomplete"
                   value="<%=request.getParameter("department")!=null?request.getParameter("department"):""%>"
                   name="department" type="text" placeholder="Department...">
            &nbsp;
        </div>
        <div style="float: left">
            <button type="submit" class="btn btn-success">Search</button>
            <button class="btn btn-success" onclick="clearAll()">Reset</button>
        </div>
    </form>
    <table class="table table-bordered table-striped" id="myTable">
        <thead>
        <tr>
            <th onclick="sortById()">Employee Id</th>
            <th onclick="sort(1)">First Name</th>
            <th onclick="sort(2)">Last Name</th>
            <th>Birthday</th>
            <th onclick="sort(4)">Position</th>
            <th onclick="sort(5)">Department</th>
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

    function sort(n) {
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
                x = rows[i].getElementsByTagName("TD")[n];
                y = rows[i + 1].getElementsByTagName("TD")[n];
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
        document.getElementById("fname").value = "";
        document.getElementById("lname").value = "";
        document.getElementById("birthday").value = "";
        document.getElementById("pos_autocomplete").value = "";
        document.getElementById("dep_autocomplete").value = "";
    }
</script>

</body>
</html>