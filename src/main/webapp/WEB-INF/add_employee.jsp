<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        var positions;
        var departments;
        $(document).ready(function() {
            var positionsAsString =  '<%=(String)request.getAttribute("positions")%>';
            var departmentsAsString = '<%=(String)request.getAttribute("departments")%>';
            positions = positionsAsString.split(",");
            departments = departmentsAsString.split(",");
            positionAutocomplete();
            departmentAutocomplete();
        });
        function positionAutocomplete (){
            $("#pos_autocomplete").autocomplete({
                source: positions,
                minLength: 0,
                scroll: true
            }).focus(function() {
                $("#pos_autocomplete").autocomplete("search");
            });
        }
        function departmentAutocomplete (){
            $("#dep_autocomplete").autocomplete({
                source: departments,
                minLength: 0,
                scroll: true
            }).focus(function() {
                $("#dep_autocomplete").autocomplete("search");
            });
        }
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
                    <td>${emp.getPositionName()}</td>
                    <td>${emp.getDepartmentName()}</td>
                </tr>
            </c:forEach>
            <tr>

                <td>
                    <label>
                        <input type="text" name="fname" required="required">
                    </label>
                </td>
                <td>
                    <label>
                        <input type="text" name="lname" required="required">
                    </label>
                </td>
                <td>
                    <label>
                        <input type="date" name="birthday" required="required">
                    </label></td>
                <td>
                    <label>
                        <input id="pos_autocomplete" name="position" required="required">
                    </label></td>
                <td>
                    <label>
                        <input id="dep_autocomplete" name="department" required="required">
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