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
    <form action="search-emp" method="post">
        <div style="float: left; margin-right: 20px">
            <p>Search for the employee first name</p>
            <input class="form-control" value="<%=request.getParameter("fname")!=null?request.getParameter("fname"):""%>" name="fname" type="text" placeholder="First Name...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the employee last name</p>
            <input class="form-control" value="<%=request.getParameter("lname")!=null?request.getParameter("lname"):""%>" name="lname" type="text" placeholder="Last Name...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the employee birthday</p>
            <input class="form-control" value="<%=request.getParameter("birthday")!=null?request.getParameter("birthday"):""%>" name="birthday" type="text" placeholder="Birthday...">
            &nbsp;
        </div>

        <div style="float: left; margin-right: 20px">
            <p>Search for the position</p>
            <input class="form-control" id="pos_autocomplete" value="<%=request.getParameter("position")!=null?request.getParameter("position"):""%>" name="position" type="text" placeholder="Position...">
            &nbsp;
        </div>

        <div style="float: left">
            <p>Search for the department</p>
            <input class="form-control" id="dep-autocomplete" value="<%=request.getParameter("department")!=null?request.getParameter("department"):""%>" name="department" type="text" placeholder="Department...">
            &nbsp;
        </div>
        <div style="float: left">
            <button type="submit" class="btn btn-success">Search</button>
        </div>
    </form>
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

<%--onkeyup="filter(id, 3)"--%>
<%--<script>--%>
<%--    function filter(inputId, index) {--%>
<%--        var input, filter, table, tr, td, i, txtValue;--%>
<%--        input = document.getElementById(inputId);--%>
<%--        filter = input.value.toUpperCase();--%>
<%--        table = document.getElementById("myTable");--%>
<%--        tr = table.getElementsByTagName("tr");--%>

<%--        for (i = 0; i < tr.length; i++) {--%>
<%--            td = tr[i].getElementsByTagName("td")[index];--%>
<%--            if (td) {--%>
<%--                txtValue = td.textContent || td.innerText;--%>
<%--                if (txtValue.toUpperCase().indexOf(filter) > -1) {--%>
<%--                    tr[i].style.display = "";--%>
<%--                } else {--%>
<%--                    tr[i].style.display = "none";--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>
</body>
</html>