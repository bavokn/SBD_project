<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Appointment</title>
</head>
<body>
<div align="center">
        <form method="GET" action="appointment/getAllAppointments">
            <label for="allAppointmentsButton">Enter User ID here :</label>
            <input id="allAppointmentsButton" type="text" name="userId" />
            <input type="submit" value="see all appointments">
        </form>
    <table cellpadding="5">
        <caption><h2>List of appointmments</h2></caption>
        <tr>
            <th>Date</th>
            <th>doctor name</th>
            <th>Doctor ID</th>
        </tr>

        <c:forEach var="appointment" items="${requestScope.appointments}">
            <tr>
                <td><c:out value="${appointment.datetime}"/></td>
                <td><c:out value="${appointment.name}" /></td>
                <td><c:out value="${appointment.doctorId}"/></td>
            </tr>
            <td>
                <form method="GET" action="delete">
                    <input hidden type="text" name="date" value="${appointment.datetime}"/>
                    <input hidden type="text" name="userId" value="${appointment.userId}"/>
                    <input hidden type="text" name="doctorId" value="${appointment.doctorId}"/>
                    <input type="submit" value="DELETE">
                </form>
                <form method="GET" action="edit">
                    <input type="text" name="date" value="${appointment.datetime}"/>
                    <input type="text" name="userId" value="${appointment.userId}"/>
                    <input type="text" name="doctorId" value="${appointment.doctorId}"/>
                    <input type="submit" value="EDIT">
                </form>
            </td>
        </c:forEach>
    </table>
    <h4 align="center">Make new appointment : </h4>
    <form method="GET" action="new">
        <label for="dateInput">Date of appointment : </label>
        <input id="dateInput" type="text" name="date" />
        <input id="userIdInput" type="text" name="userId" />
        <input id="doctorIdInput" type="text" name="doctorId"/>
        <input type="submit" value="NEW">
    </form>
    <p><c:out value="${requestScope.error}"/></p>
</div>
</body>
</html>
