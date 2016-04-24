<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Neveral
  Date: 10.11.15
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Storage Settings</title>
</head>
<body>
<center>
    <H4>Storage is created with settings:</H4>
    <table style="border: 1px solid black; width: 300px;">
        <tr>
            <td>Name:</td>
            <td>${name}</td>
        </tr>
        <tr>
            <td>Location:</td>
            <td>${location}</td>
        </tr>
        <tr>
            <td>Storage Type:</td>
            <td>${accType}</td>
        </tr>
        <tr>
            <td>Resource Group:</td>
            <td>${group}</td>
        </tr>
    </table>
</center>
</body>
</html>
