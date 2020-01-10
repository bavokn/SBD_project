<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bavo
  Date: 8/1/20
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Doctor</title>
</head>
<body>
<form method="GET" action="doctor/getAllDoctorAppointments">
    <label for="allAppointmentsButton">Enter doctor ID here :</label>
    <input id="allAppointmentsButton" type="text" name="doctorId" />
    <input type="submit" value="see all appointments">
</form>
<table cellpadding="5" align="center">
<caption><h2>List of appointmments</h2></caption>
<tr>
    <th>Date</th>
    <th>UserId</th>
    <th>user name</th>
</tr>

<c:forEach var="appointment" items="${requestScope.appointments}">
    <tr>
        <td><c:out value="${appointment.datetime}"/></td>
        <td><c:out value="${appointment.userId}" /></td>
        <td><c:out value="${appointment.name}" /></td>
        <td>
            <form method="get" action="doctor/markAbsent">
                <input hidden value="${appointment.datetime}" name="date">
                <input hidden value="${appointment.userId}" name="userId"/>
                <input hidden value="${appointment.doctorId}" name="doctorId"/>
                <input type="submit" value="mark absent"/>
            </form>
        </td>
    </tr>
</c:forEach>
</table>

<table cellpadding="5" align="center">
    <caption><h2>List of timeframes</h2></caption>
    <tr>
        <th>begin</th>
        <th>end</th>
    </tr>

    <c:forEach var="timeframe" items="${requestScope.timeframes}">
        <tr>
            <td><c:out value="${timeframe.begindt}"/></td>
            <td><c:out value="${timeframe.endDt}" /></td>
        </tr>
        <tr>
            <td>
                <form method="get" action="doctor/updateTimeFrame">
                    <input hidden type="text" value="${timeframe.begindt}"  name ="beginDt">
                    <input hidden type="text" value="${timeframe.endDt}"  name = "endDt">
                    <input hidden type="text" value="${timeframe.doctorId}"  name = "doctorId">
                    <label for="newBeginDt">new begin date :</label>
                    <input id="newBeginDt" type="text" name="newBeginDt">
                    <label for="newEndDt">new end date :</label>
                    <input id="newEndDt" type="text" name="newEndDt">
                    <input type="submit" value="EDIT">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<div align="center">
    <form method="GET" action="doctor/insertTimeFrame">
        <label for="beignDateInput">begin of timeframe : </label>
        <input id="beignDateInput" type="text" name="beginDt" />
        <label for="endDateInput">end of timeframe : </label>
        <input id="endDateInput" type="text" name="endDt" />
        <input id="doctorIdInput" type="text" name="doctorId"/>
        <input type="submit" value="NEW">
    </form>
</div>


<p align="center"><c:out value="${requestScope.error}"/></p>
</body>
</html>
