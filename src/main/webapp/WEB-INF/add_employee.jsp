<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.hrant.dto.PositionDto" %>
<%@ page import="com.hrant.dto.DepartmentDto" %>
<%@ page import="com.hrant.servlet.EmployeesServlet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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

            $("#dep_autocomplete").autocomplete({
                source: departments
            });
        });
    </script>

</head>
<body>
<form action="add-emp" method="post">
    <div class="container">
        <h3>Employees</h3>
        <ul class="nav nav-tabs">
            <li class="active"><a href="employees">Employees</a></li>
            <li><a href="attendance-records">Attendance records</a></li>
        </ul>
        <br>
    </div>

    <div class="container">
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
                    <td>${emp.getFName()}</td>
                    <td>${emp.getLName()}</td>
                    <td>${emp.getBirthday()}</td>
                    <td>${EmployeesServlet.findPositionById(emp.getPositionId()).name}</td>
                    <td>${EmployeesServlet.findDepartmentById((emp.getDepartmentId())).name}</td>
                </tr>
            </c:forEach>
            <tr>

                <td>
                    <label>
                        <input type="text" value="<c:out value='${e.fname}' />" name="fname"
                               required="required">
                    </label>
                </td>
                <td>
                    <label>
                        <input type="text" value="<c:out value='${e.lname}' />" name="lname"
                               required="required">
                    </label>
                </td>
                <td>
                    <label>
                        <input type="date" value="<c:out value='${e.birthday}' />" name="birthday"
                               required="required">
                    </label></td>
                <td>
                    <label>
                        <input id="pos_autocomplete" value="<c:out value='${e.position}' />" name="position"
                               required="required">
                    </label></td>
                <td>
                    <label>
                        <input id="dep_autocomplete" value="<c:out value='${e.department}' />" name="department"
                               required="required">
                    </label></td>
                <td>
                    <button type="submit" class="btn btn-success">Save</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>