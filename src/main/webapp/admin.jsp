<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>

<body>
<h1>Welome to the admin page</h1>

<table align="center">
    <tr>
        <td>
            <table>
            <tr>
                <td>
                <div align="center">
                    <form method="POST" action="admin/insertOrUpdateDoctor">
                        <table>
                            <tr>
                                <td>
                                    <label for="salaryNew">salary :</label>
                                    <input id="salaryNew" type="text" name="salary">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="doctorIDNew">doctor ID :</label>
                                    <input id="doctorIDNew" type="text" name="doctorId">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="nifNew">NIF :</label>
                                    <input id="nifNew" type="text" name="nif">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="firstNameNew">firstname :</label>
                                    <input id="firstNameNew" type="text" name="firstName">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="lastNameNew">lastname :</label>
                                    <input id="lastNameNew" type="text" name="lastName">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="birthdateNew">birthdate :</label>
                                    <input id="birthdateNew" type="text" name="birthDate">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="photoNew">photo url :</label>
                                    <input id="photoNew" type="text" name="photo">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="addressNew">address :</label>
                                    <input id="addressNew" type="text" name="address">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="specialtyNameNew">specialty name :</label>
                                    <input id="specialtyNameNew" type="text" name="specialtyName">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="submit" name="insert" value="CREATE NEW DOCTOR">
                                    <input type="submit" name="update" value="UPDATE DOCTOR">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </td>
         </tr>
        </table>
        </td>
        <td>
            <table>
                <tr>
                    <td>
                        <div align="center">
                            <form method="POST" action="admin/insertOrUpdatetUser">
                                <table>
                                    <tr>
                                        <td>
                                            <label for="userId">user ID :</label>
                                            <input id="userId" type="text" name="userId">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="nif">NIF :</label>
                                            <input id="nif" type="text" name="nif">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="firstname">firstname :</label>
                                            <input id="firstName" type="text" name="firstName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="lastname">lastname :</label>
                                            <input id="lastname" type="text" name="lastName">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="birthdate">birthdate :</label>
                                            <input id="birthdate" type="text" name="birthDate">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="photo">photo url :</label>
                                            <input id="photo" type="text" name="photo">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="address">address :</label>
                                            <input id="address" type="text" name="address">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label for="gender">gender (M/F) :</label>
                                            <input id="gender" type="text" name="gender">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="submit" name="insert" value="CREATE NEW USER">
                                            <input type="submit" name="update" value="UPDATE USER">
                                            <input type="submit" name="save" value="SAVE DOCTOR TO XML">
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

<label for="autocomplete">Look for user :</label>
<input type="text" id="autocomplete" name="autocomplete"/>

<script>
    $("#autocomplete").autocomplete("getdata.jsp");
</script>

<form method="get" action="admin/absentUsers">
    <input type="submit" value="GET FREQUENT ABSENT USERS">
</form>

<form method="get" action="admin/specialtyDoctors">
    <input type="submit" value="GET SPECIALTY WITH AMOUNT OF DOCTORS">
</form>

<form method="get" action="admin/specialtyPastAppointments">
    <input type="submit" value="APPOINTMENTS PER SPECIALTY">
</form>

<table cellpadding="5" align="center">
    <caption><h2>List of frequent absent users</h2></caption>
    <tr>
        <th>firstname</th>
        <th>lastname</th>
        <th>number of times absent</th>
    </tr>

    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td><c:out value="${user.firstName}"/></td>
            <td><c:out value="${user.lastName}" /></td>
            <td><c:out value="${user.absence_counter}"/></td>
        </tr>
    </c:forEach>
</table>

<table cellpadding="5" align="center">
    <caption><h2>Specialty with total amount of doctors</h2></caption>
    <tr>
        <th>specialty</th>
        <th>amount of doctors</th>
    </tr>

    <c:forEach var="map" items="${requestScope.specialtymap}">
        <tr>
            <td>
                <c:out value="${map.key}"/>
            </td>
            <td>
                <c:out value="${map.value}"/>
            </td>
        </tr>
    </c:forEach>
</table>

<table cellpadding="5" align="center">
    <caption><h2>appointmets per specialty</h2></caption>
    <tr>
        <th>specialty</th>
        <th>amount of appointments</th>
    </tr>

    <c:forEach var="map" items="${requestScope.specialtyPasAppointmentsmap}">
        <tr>
            <td>
                <c:out value="${map.key}"/>
            </td>
            <td>
                <c:out value="${map.value}"/>
            </td>
        </tr>
    </c:forEach>
</table>

<form method="get" action="admin/specialtyScheduledAppointments">
    <input type="submit" value="specialtyScheduledAppointments">
</form>
<table cellpadding="5" align="center">
    <caption><h2>scheduled appointmets per specialty</h2></caption>
    <tr>
        <th>specialty</th>
        <th>scheduled appointments</th>
    </tr>

    <c:forEach var="map" items="${requestScope.specialtyScheduledAppointmentsmap}">
        <tr>
            <td>
                <c:out value="${map.key}"/>
            </td>
            <td>
                <c:out value="${map.value}"/>
            </td>
        </tr>
    </c:forEach>
</table>

<form method="get" action="admin/absentAppointments">
    <input type="submit" value="absentAppointments">
</form>

<table cellpadding="5" align="center">
    <caption><h2>absent appointments per speciality</h2></caption>
    <tr>
        <th>specialty</th>
        <th>amount absent appointments</th>
    </tr>

    <c:forEach var="map" items="${requestScope.absentMap}">
        <tr>
            <td>
                <c:out value="${map.key}"/>
            </td>
            <td>
                <c:out value="${map.value}"/>
            </td>
        </tr>
    </c:forEach>
</table>

<c:forEach var="model" items="${requestScope.autocompleteModels}">
    <p><c:out value="${model.firstName}"/></p>
</c:forEach>


<%--<p><c:out value="${requestScope.error}"/></p>--%>

</body>
</html>
